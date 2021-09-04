package com.homemade.product_data

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.contains
import androidx.lifecycle.Observer
import com.floriaapp.core.domain.model.provider_.productsVendor.Tag
import com.floriaapp.core.ui.ProductsViewModel
import com.floriaapp.core.ui.objectData
import com.google.android.material.chip.Chip
import com.homemade.product_data.databinding.ActivityAddProductSecondBinding
import com.test.utils.Bases.BaseActivity
import com.test.utils.Ext.*
import com.test.utils.TAGS
import com.test.utils.TOTAL_TAGS_ALLOWED
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.FileNotFoundException
import java.io.InputStream

class AddProductSecondStepActivity : BaseActivity() {
    lateinit var binding: ActivityAddProductSecondBinding
    private val productViewModel: ProductsViewModel by viewModel()
    var tagsSpinner = HashMap<String, String>()
    var listOfImages = mutableListOf<MultipartBody.Part>()
    var tagsListInteger = mutableListOf<Int>()


    @SuppressLint("SetTextI18n")
    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                try {
                    val imageUri: Uri? = result!!.data?.data
                    val imageStream: InputStream? =
                        imageUri?.let { contentResolver.openInputStream(it) }
                    val selectedImage = BitmapFactory.decodeStream(imageStream)
                    val file = bitmapToFile(selectedImage)
                    val requestFile: RequestBody =
                        RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                    val multipartBody =
                        MultipartBody.Part.createFormData("images[]", file.name, requestFile)
                    listOfImages.add(multipartBody)
                    binding.btnUploadSecond.uploadText.text =
                        "${resources.getText(R.string.images_uploaded)} ${listOfImages.count()} "

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        attachSpinnersToData()

        productViewModel.SuccessMessage.observe(this, Observer {
            dismissProgressDialog()
            showToast("success")
        })
        productViewModel.Error.observe(this, Observer {
            dismissProgressDialog()
            showToast("Error")
        })

        binding.btnAddProduct.setOnClickListener {
            val checked = isFieldsEmpty()
            if (!checked) {
                showProgress()
                Log.i("productData", prepareData().toString())
                productViewModel.storeProduct(prepareData())
            }
        }

        binding.btnUploadSecond.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            resultLauncher.launch(photoPickerIntent)
        }
    }

    private fun prepareData(): objectData {
        with(objectData) {
            sku = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                binding.tvSku.getText()
            )
            discount = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                binding.tvProductDiscount.getText()
            )
            discount_start_date = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                binding.tvBeginDiscount.getText()
            )
            discount_end_date = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                binding.tvEndDiscount.getText()
            )
            images = listOfImages as ArrayList
            active = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                "0"
            )
            pinned = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                "0"
            )
            return this
        }

    }

    private fun isFieldsEmpty(): Boolean {
        when (true) {
            binding.tvSku.editText.text.trim().isEmpty() -> {
                binding.tvSku.editText.error = resources.getString(R.string.checkInput)
                return true
            }
//            binding.tvProductDiscount.editText.text.trim().isEmpty() -> {
//                binding.tvProductDiscount.editText.error = resources.getString(R.string.checkInput)
//                return true
//            }
            else -> return false
        }
    }

    private fun attachSpinnersToData() {
        val tagsList = ArrayList<String>()

        tagsList.add(resources.getString(R.string.choose_opening))
        getList(TAGS).fromJson<MutableList<Tag>>()?.forEachIndexed { index, tag ->
            tagsList.add(tag.name)
            tagsSpinner[tag.name] = tag.id
        }
        val spinnerAdapter3: ArrayAdapter<String?> = createSpinner(tagsList, fromTags = true)
        binding.spinnerTags.spinner.adapter = spinnerAdapter3
        binding.spinnerTags.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    if (binding.chipGroup.childCount >= TOTAL_TAGS_ALLOWED) {
                        showToast(resources.getString(R.string.tags_allowed_message))
                        return
                    }
                    if (position == 0) {
                        if (view is TextView) {
                            (view as TextView).setTextColor(resources.getColor(com.test.utils.R.color.colorgray))
                        }
                    }
                    if (position != 0) {
                        val selectedItemId =
                            tagsSpinner[adapterView?.getItemAtPosition(position).toString()]!!
                        if (!tagsListInteger.contains(selectedItemId.toInt())) {
                            tagsListInteger.add(selectedItemId.toInt())

                            val chip = createChip(
                                Tag(
                                    id = selectedItemId,
                                    name = tagsSpinner.filterValues { it == selectedItemId }.keys.distinct()
                                        .iterator().next()
                                )
                            )
                            if (!binding.chipGroup.contains(chip)) binding.chipGroup.addView(chip)
                        }
                    }


                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

    }

    private fun createChip(tag: Tag): Chip {
        val chip = Chip(this)
        chip.text = tag.name
        chip.id = tag.id.toInt()
        chip.setChipBackgroundColorResource(com.test.utils.R.color.blue226)
        chip.setTextAppearanceResource(com.test.utils.R.style.ChipTextStyle_Selected);
        return chip
    }


}
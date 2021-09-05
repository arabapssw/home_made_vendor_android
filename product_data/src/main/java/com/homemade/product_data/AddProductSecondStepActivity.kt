package com.homemade.product_data

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.contains
import androidx.lifecycle.Observer
import com.floriaapp.core.domain.model.provider_.productsVendor.ProviderProductsResponseItem
import com.floriaapp.core.domain.model.provider_.productsVendor.Tag
import com.floriaapp.core.ui.ProductsViewModel
import com.floriaapp.core.ui.objectData
import com.google.android.material.chip.Chip
import com.homemade.product_data.databinding.ActivityAddProductSecondBinding
import com.test.utils.Bases.BaseActivity
import com.test.utils.EDIT_PRODUCT
import com.test.utils.Ext.*
import com.test.utils.PRODUCT_DATA
import com.test.utils.TAGS
import com.test.utils.TOTAL_TAGS_ALLOWED
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.FileNotFoundException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AddProductSecondStepActivity : BaseActivity() {
    lateinit var binding: ActivityAddProductSecondBinding
    private val productViewModel: ProductsViewModel by viewModel()
    var tagsSpinner = HashMap<String, String>()
    var listOfImages = mutableListOf<MultipartBody.Part>()
    var tagsListInteger = mutableListOf<Int>()
     var calenderInMilis:Long = 0L
    lateinit var productData:ProviderProductsResponseItem


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
        playWithViews()
        attachSpinnersToData()


        val data = intent.extras
        val isEdit = data?.getBoolean(EDIT_PRODUCT, false)
        if (isEdit == true) {
            productData = data.get(PRODUCT_DATA) as ProviderProductsResponseItem
            bindDataToViews(productData)

        }

        productViewModel.SuccessMessage.observe(this, Observer {
            dismissProgressDialog()
            showToast(it.message)
        })
        productViewModel.Error.observe(this, Observer {
            dismissProgressDialog()
            showToast(it)
        })

        binding.btnAddProduct.setOnClickListener {
            val checked = isFieldsEmpty()
            if (!checked) {
                showProgress()
                Log.i("productData", prepareData().toString())
                if (isEdit == true) productViewModel.editProduct(prepareData(),productData.id)
                else productViewModel.storeProduct(prepareData())
            }
        }

        binding.btnUploadSecond.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            resultLauncher.launch(photoPickerIntent)
        }
    }

    private fun bindDataToViews(productData: ProviderProductsResponseItem) {
        with(binding){
            tvSku.editText.setText(productData.sku)
            tvProductDiscount.editText.setText(productData.discount.toString())
            tvBeginDiscount.editText.setText(productData.discountStartDate)
            tvEndDiscount.editText.setText(productData.discountEndDate)
            productStatusCheck.switchCompat.isChecked = productData.active==1
            productPinCheck.switchCompat.isChecked = productData.pinned==1
            productData.tags.forEachIndexed { index, tag ->
                val chip = createChip(
                    Tag(
                        id = tag.id,
                        name = tag.name
                    )
                )
                if (!binding.chipGroup.contains(chip)) binding.chipGroup.addView(chip)
            }


        }
    }

    private fun playWithViews() {
        binding.btnUploadSecond.uploadText.text = resources.getString(R.string.upload_images)
        binding.tvBeginDiscount.editText.isFocusable = false
        binding.tvEndDiscount.editText.isFocusable = false

        binding.tvBeginDiscount.editText.setOnClickListener {
            openDatePicker(binding.tvBeginDiscount.editText,true)
        }
        binding.tvEndDiscount.editText.setOnClickListener {
            if (binding.tvBeginDiscount.editText.text.isEmpty()){
                showToast(resources.getString(R.string.chooseStartDate))
                return@setOnClickListener
            }
            openDatePicker(binding.tvEndDiscount.editText, false)
        }
    }

    private fun openDatePicker(editText: EditText, begin: Boolean) {
        val calndar = Calendar.getInstance()

        // create an OnDateSetListener
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calndar.set(Calendar.YEAR, year)
                calndar.set(Calendar.MONTH, monthOfYear)
                calndar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val backendFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(backendFormat, Locale.ENGLISH)
                if (begin) calenderInMilis = calndar.timeInMillis


                editText.setText(sdf.format(calndar.time))
            }

        val datePicker = DatePickerDialog(
            this, com.test.utils.R.style.DialogTheme,
            dateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            calndar.get(Calendar.YEAR),
            calndar.get(Calendar.MONTH),
            calndar.get(Calendar.DAY_OF_MONTH)
        )
        if (begin)datePicker.datePicker.minDate = System.currentTimeMillis() - 1000;
        else datePicker.datePicker.minDate = calenderInMilis

        datePicker.show()
        datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(
            com.test.utils.R.color.teaBlue));
        datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(
            com.test.utils.R.color.teaBlue));

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
                "text/plain".toMediaTypeOrNull(),if (binding.productStatusCheck.switchCompat.isChecked) "1" else "0"

            )
            pinned = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                if (binding.productPinCheck.switchCompat.isChecked) "1" else "0"
            )
            tags = tagsListInteger
            return this
        }

    }

    private fun isFieldsEmpty(): Boolean {
        when (true) {
            binding.tvSku.editText.text.trim().isEmpty() -> {
                binding.tvSku.editText.error = resources.getString(R.string.checkInput)
                return true
            }

            binding.tvProductDiscount.editText.text.trim().isEmpty() -> {

                binding.tvProductDiscount.editText.error = resources.getString(R.string.checkInput)
                return true
            }
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
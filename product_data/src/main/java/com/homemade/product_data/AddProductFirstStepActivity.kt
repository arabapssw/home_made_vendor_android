package com.homemade.product_data


import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import com.floriaapp.core.domain.model.general.Categories
import com.floriaapp.core.ui.ProductsViewModel
import com.floriaapp.core.ui.objectData
import com.homemade.product_data.databinding.ActivityAddProductFirstStepBinding
import com.test.utils.Bases.BaseActivity
import com.test.utils.Ext.bitmapToFile
import com.test.utils.Ext.createSpinner
import com.test.utils.Ext.saveList
import com.test.utils.Ext.showToast
import com.test.utils.NavigationUtils
import com.test.utils.TAGS
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.FileNotFoundException
import java.io.InputStream

class AddProductFirstStepActivity : BaseActivity() {
    lateinit var binding: ActivityAddProductFirstStepBinding
    lateinit var imagePath: MultipartBody.Part
    private val productViewModel: ProductsViewModel by viewModel()
    var categoriesSpinner = HashMap<String, String>()
    var categoryList = mutableListOf<Int>()


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
                        MultipartBody.Part.createFormData("image", file.name, requestFile)
                    imagePath = multipartBody
                    binding.btnUpload.uploadText.text = resources.getText(R.string.upload_success)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductFirstStepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNextAdd.setOnClickListener {
            val checkingAll = isFieldsEmpty()
            if (!checkingAll) {
                prepareData()
                NavigationUtils.goToDestination2(
                    this,
                    AddProductSecondStepActivity::class.java
                )
            }


        }

        binding.btnUpload.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            resultLauncher.launch(photoPickerIntent)
        }


        showProgress()
        productViewModel.getGeneralData()
        productViewModel.GeneralData.observe(this, Observer {
            dismissProgressDialog()
            attachSpinnersToData(it.data.categories)
            saveList(TAGS, it.data.tags)
        })


    }

    fun prepareData() {
        with(objectData) {
            nameArabic = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                binding.tvNameProductAr.getText()
            )
            nameEnglish = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                binding.tvNameProductEn.getText()
            )
            categories = categoryList
            image = imagePath
            descriptionArabic = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                binding.tvDescriptionProductAr.getText()
            )
            descriptionEnglish = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                binding.tvDescriptionProductEn.getText()
            )
            price = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                binding.tvProductPrice.getText()
            )
            weight = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                binding.tvProductQuantity.getText()
            )

        }
    }

    private fun attachSpinnersToData(categoriess: List<Categories>) {
        val categories = ArrayList<String>()
        categoriess.forEachIndexed { index, category ->
            categories.add(category.name)
            categoriesSpinner[category.name] = category.id
        }
        val spinnerAdapter3: ArrayAdapter<String?> = createSpinner(categories, fromTags = false)
        binding.spinnerTags.spinner.adapter = spinnerAdapter3
        binding.spinnerTags.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedItemId =
                        categoriesSpinner[adapterView?.getItemAtPosition(position).toString()]!!
                    //  showToast(selectedItemId.toString())
                    categoryList.add(selectedItemId.toInt())

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

    }

    private fun isFieldsEmpty(): Boolean {
        when (true) {
            binding.tvNameProductAr.editText.text.trim().isEmpty() -> {
                binding.tvNameProductAr.editText.error = resources.getString(R.string.checkInput)
                return true
            }
            binding.tvNameProductEn.editText.text.trim().isEmpty() -> {
                binding.tvNameProductEn.editText.error = resources.getString(R.string.checkInput)
                return true
            }
            binding.tvDescriptionProductAr.editText.text.trim().isEmpty() -> {
                binding.tvDescriptionProductAr.editText.error =
                    resources.getString(R.string.checkInput)
                return true
            }

            binding.tvNameProductAr.editText.text.trim().length < 3 -> {

                binding.tvNameProductAr.editText.error = resources.getString(R.string.inputLength)
                return true
            }
            binding.tvNameProductEn.editText.text.trim().length < 3 -> {

                binding.tvNameProductEn.editText.error = resources.getString(R.string.inputLength)
                return true
            }

            binding.tvDescriptionProductAr.editText.text.trim().length < 3 -> {

                binding.tvDescriptionProductAr.editText.error = resources.getString(R.string.inputLength)
                return true
            }


            binding.tvDescriptionProductEn.editText.text.trim().isEmpty() -> {
                binding.tvDescriptionProductEn.editText.error =
                    resources.getString(R.string.checkInput)
                return true
            }

            binding.tvDescriptionProductEn.editText.text.trim().length < 3 -> {
                binding.tvDescriptionProductEn.editText.error = resources.getString(R.string.inputLength)
                return true
            }
            binding.tvProductPrice.editText.text.trim().isEmpty() -> {
                binding.tvProductPrice.editText.error =
                    resources.getString(R.string.checkInput)
                return true
            }

            binding.tvProductQuantity.editText.text.trim().isEmpty() -> {
                binding.tvProductQuantity.editText.error =
                    resources.getString(R.string.checkInput)
                return true
            }

            !::imagePath.isInitialized -> {
                showToast(resources.getString(R.string.upload_image_please))
                return true
            }

            else -> return false
        }
    }
}
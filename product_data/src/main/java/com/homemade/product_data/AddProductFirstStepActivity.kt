package com.homemade.product_data


import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.homemade.product_data.databinding.ActivityAddProductFirstStepBinding
import com.test.utils.Bases.BaseActivity
import com.test.utils.Ext.bitmapToFile
import com.test.utils.Ext.showToast
import com.test.utils.NavigationUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.FileNotFoundException
import java.io.InputStream

class AddProductFirstStepActivity : BaseActivity() {
    lateinit var binding:ActivityAddProductFirstStepBinding
    lateinit var imagePath:MultipartBody.Part

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                try {
                    val imageUri: Uri? = result!!.data?.data
                    val imageStream: InputStream? =
                        imageUri?.let {contentResolver.openInputStream(it) }
                    val selectedImage = BitmapFactory.decodeStream(imageStream)
                    val file = bitmapToFile(selectedImage)
                    val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                    val multipartBody = MultipartBody.Part.createFormData("attachment", file.name, requestFile)
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
            if (!checkingAll) NavigationUtils.goToDestination2(this,AddProductSecondStepActivity::class.java)
        }

        binding.btnUpload.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            resultLauncher.launch(photoPickerIntent)
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
                binding.tvDescriptionProductAr.editText.error = resources.getString(R.string.checkInput)
                return true
            }
            binding.tvDescriptionProductEn.editText.text.trim().isEmpty() -> {
                binding.tvDescriptionProductEn.editText.error = resources.getString(R.string.checkInput)
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

            !::imagePath.isInitialized ->{
                showToast(resources.getString(R.string.upload_image_please))
                return true
            }

            else -> return false
        }
    }
}
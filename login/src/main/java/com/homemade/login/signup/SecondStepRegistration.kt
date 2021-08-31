package com.homemade.login.signup

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.floriaapp.core.domain.model.User
import com.floriaapp.core.domain.model.general.Nationality
import com.floriaapp.core.domain.model.login.registerBody
import com.floriaapp.core.domain.model.login.registerBodyString
import com.floriaapp.core.ui.LoginViewModel
import com.google.android.material.button.MaterialButton
import com.homemade.home.HomeActivity
import com.homemade.login.R
import com.homemade.login.databinding.FragmentSecondStepRegistraionBinding
import com.test.utils.*
import com.test.utils.Bases.BaseActivity
import com.test.utils.Ext.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set


class SecondStepRegistration : BaseActivity() {

    lateinit var binding: FragmentSecondStepRegistraionBinding
    var countries = HashMap<String, String>()
    var nationalities = HashMap<String, String>()
    var countriesList = mutableListOf<String>()
    var nationalitiesList = mutableListOf<String>()
    var countryId: String = ""
    var nationalityId: String = ""
    var genderChosen = MALE
    private val loginViewModel: LoginViewModel by viewModel()
    private val sharePrefrence: SharedPreferences.Editor by inject()
    var imagePath: MultipartBody.Part? = null

    lateinit var codeNeeded:String



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
                        MultipartBody.Part.createFormData("avatar", file.name, requestFile)
                    imagePath = multipartBody
                    binding.iv.setImageBitmap(selectedImage)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSecondStepRegistraionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.extras?.getParcelable<registerBodyString>(FIRST_STEP_REGISTRATION)

        binding.btnSave.setOnClickListener {
            with(data!!) {

                val registerBody = registerBody(
                    first_name = getRequestBodyOfString(first_name),
                    last_name = getRequestBodyOfString(last_name),
                    phone = getRequestBodyOfString(phone),
                    email = getRequestBodyOfString(email),
                    password = password,
                    password_confirmation = password_confirmation,
                    terms = terms,
                    country_id = countryId.toInt(),
                    nationality = getRequestBodyOfString(nationalityId),
                    gender = getRequestBodyOfString(genderChosen),
                    avatar = imagePath
                )
                Log.i("rgistr", registerBody.toString())


                showProgress()
                loginViewModel.register(registerBody)

            }
        }
        //This will make it as RequestBody to pass it along image/files.

        loginViewModel.SuccessVerification.observe(this, {
            dismissProgressDialog()
            sharePrefrence.putBoolean(USER_ISVERIFIED, true).commit()

            showSuccessVerification(function = {
                NavigationUtils.goToDestinationWithClearTasks(this, HomeActivity::class.java)
            })
        })

        loginViewModel.SuccessSendingVerification.observe(this, androidx.lifecycle.Observer {
            dismissAnyDialog()
            showVerificationDialog(loginViewModel, VERIFY_NEW_USER){code ->
                codeNeeded  = code
            }
        })
        loginViewModel.RegisterResponse.observe(this, {
            sharePrefrence.putString(TOKEN_USER, it.meta.accessToken).commit()
            with(it.registratedUser) {
                saveObject(
                    USER_DATA,
                    User(
                        firstName,
                        lastName,
                        phone,
                        avatar,
                        gender,
                        email,
                        country.name,
                        nationality.value,
                        wallet_balance = walletBalance
                    )
                )
            }
            dismissProgressDialog()
            showVerificationDialog(loginViewModel, VERIFY_NEW_USER){code ->
                codeNeeded  = code
            }
        })
        loginViewModel.Error.observe(this, {
            showErrorDialog(it)
            dismissProgressDialog()
        })

        binding.iv.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            resultLauncher.launch(photoPickerIntent)
        }

        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (group.checkedButtonId) {
                    R.id.btn_male -> {
                        colorizeButtons(binding.btnMale, binding.btnFemale)
                        genderChosen = MALE
                    }
                    R.id.btn_female -> {
                        genderChosen = FEMALE
                        colorizeButtons(binding.btnFemale, binding.btnMale)
                    }

                }
            }
        }


        getList(COUNTRIES)
            .fromJson<MutableList<com.floriaapp.core.domain.model.general.Country>>()
            ?.forEachIndexed { index, country ->
                countries[country.name] = country.id
                countriesList.add(country.name)

            }

        createAdapter(countriesList, binding.spinnerCountries)

        getList(NATIONALITIES).fromJson<MutableList<Nationality>>()
            ?.forEachIndexed { index, nationality ->
                nationalities[nationality.name] = nationality.id
                nationalitiesList.add(nationality.name)
            }
        createAdapter(nationalitiesList, binding.spinnerNationality)


    }

    private fun getRequestBodyOfString(genderChosen: String) =
        RequestBody.create("text/plain".toMediaTypeOrNull(), genderChosen)

    private fun createAdapter(list: MutableList<String>, spinner: Spinner?) {
        val countriesAdapter: ArrayAdapter<String?> = createSpinner(list as ArrayList<String>)
        spinner?.adapter = countriesAdapter
        spinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (spinner == binding.spinnerCountries) {
                        val selectedItem = adapterView?.getItemAtPosition(position).toString();
                        val selectedId = countries[selectedItem]
                        countryId = selectedId!!

                    } else {
                        val selectedItem = adapterView?.getItemAtPosition(position).toString();
                        val selectedId = nationalities[selectedItem]
                        nationalityId = selectedId!!
                    }


                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private fun colorizeButtons(primaryBtn: MaterialButton, secondryBtn: MaterialButton) {
        primaryBtn.backgroundTintList = resources?.getColorStateList(R.color.teaBlue);
        primaryBtn.setTextColor(resources.getColor(R.color.colorWhite))
        secondryBtn.backgroundTintList = resources?.getColorStateList(R.color.colorWhite);
        secondryBtn.setTextColor(resources.getColor(R.color.teaBlue))

    }
}

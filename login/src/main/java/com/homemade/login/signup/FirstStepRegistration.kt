package com.homemade.login.signup

import android.content.Intent
import android.os.Bundle
import com.floriaapp.core.domain.model.User
import com.floriaapp.core.domain.model.login.registerBody
import com.floriaapp.core.domain.model.login.registerBodyString
import com.floriaapp.core.ui.LoginViewModel
import com.homemade.login.R
import com.homemade.login.databinding.FragmentFirstStepRegistrationBinding
import com.test.utils.Bases.BaseActivity
import com.test.utils.Ext.saveObject
import com.test.utils.Ext.showToast
import com.test.utils.FIRST_STEP_REGISTRATION
import com.test.utils.FROM_FAQ
import com.test.utils.TERMS_AND_CONDITIONS
import com.test.utils.USER_DATA
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.koin.android.viewmodel.ext.android.viewModel


class FirstStepRegistration : BaseActivity() {

    lateinit var binding: FragmentFirstStepRegistrationBinding
    lateinit var registerBody: registerBody

    private val loginViewModel: LoginViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFirstStepRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvCheck.setOnClickListener {
            val intent= Intent(this, Class.forName(TERMS_AND_CONDITIONS))
            intent.putExtra(FROM_FAQ,false)
            startActivity(intent)
        }
        binding.creatAccount.setOnClickListener {
            val isFieldEmpty = isFieldsEmpty()
            if (!isFieldEmpty) {
                with(binding) {
                    registerBody = registerBody(
                        first_name = getRequestBodyOfString(customFirstName.getText()),
                        last_name = getRequestBodyOfString(customSecondName.getText()),
                        phone = getRequestBodyOfString(customTelephone.getText()),
                        email = getRequestBodyOfString(customEmail.getText()),
                        password = customPasswordRegister.getText(),
                        password_confirmation = customConfirmPassword.getText(),
                        terms = 1,
                        gender = null,
                        country_id = null,
                        nationality = null,
                        avatar = null
                    )
//                    registerBody = registerBody(
//                        first_name = "test",
//                        last_name = "test", phone = getRequestBodyOfString("501111125"),
//                        email = getRequestBodyOfString("lola333@test.com"), password = "12345678",
//                        password_confirmation = "12345678", terms = 1,
//                        gender = null,
//                        country_id = null,
//                        nationality = null,
//                        avatar = null)
                    showProgress()
                    loginViewModel.registerFirstStep(registerBody = registerBody)

                }


            }
        }

        binding.tvSignIn.setOnClickListener {
            finish()
        }

        loginViewModel.Error.observe(this, {
            showToast(it)
            dismissProgressDialog()
        })


        loginViewModel.RegisterResponse.observe(this, {
            dismissProgressDialog()

            with(binding) {
                val registerBodys = registerBodyString(
                    first_name = customFirstName.getText(),
                    last_name = customSecondName.getText(), phone = customTelephone.getText(),
                    email = customEmail.getText(), password = customPasswordRegister.getText(),
                    password_confirmation = customConfirmPassword.getText(), terms = 1,
                    gender = null,
                    country_id = null,
                    nationality = null,
                    avatar = null
                )

                val intent = Intent(this@FirstStepRegistration, SecondStepRegistration::class.java)
                intent.putExtra(FIRST_STEP_REGISTRATION, registerBodys)
                startActivity(intent)

            }
        })
    }



    private fun getRequestBodyOfString(genderChosen: String) =
        RequestBody.create("text/plain".toMediaTypeOrNull(), genderChosen)


    private fun isFieldsEmpty(): Boolean {
        when (true) {
            binding.customFirstName.editText.text.trim().isEmpty() -> {
                binding.customFirstName.editText.error = resources.getString(R.string.checkInput)
                return true
            }
            binding.customSecondName.editText.text.trim().isEmpty() -> {
                binding.customSecondName.editText.error = resources.getString(R.string.checkInput)
                return true
            }
            binding.customTelephone.editText.text.trim().isEmpty() -> {
                binding.customTelephone.editText.error = resources.getString(R.string.checkInput)
                return true
            }
            binding.customEmail.editText.text.trim().isEmpty() -> {
                binding.customEmail.editText.error = resources.getString(R.string.checkInput)
                return true
            }

            binding.customPasswordRegister.editText.text.trim().isEmpty() -> {
                binding.customPasswordRegister.editText.error =
                    resources.getString(R.string.checkInput)
                return true
            }

            binding.customPasswordRegister.editText.text.trim().length < 8 -> {
                binding.customPasswordRegister.editText.error =
                    resources.getString(R.string.shortPassword)
                return true
            }

            binding.customConfirmPassword.editText.text.trim().length < 8 -> {
                binding.customConfirmPassword.editText.error =
                    resources.getString(R.string.shortPassword)
                return true
            }


            binding.customConfirmPassword.editText.text.trim().isEmpty() -> {
                binding.customConfirmPassword.editText.error =
                    resources.getString(R.string.checkInput)
                return true
            }
            !binding.check.isChecked -> {
                showToast(resources.getString(R.string.accpt_terms_title))
                return true
            }


            else -> return false
        }
    }
}
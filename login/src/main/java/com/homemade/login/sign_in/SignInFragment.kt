package com.homemade.login.sign_in

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import com.floriaapp.core.domain.model.User
import com.floriaapp.core.ui.LoginViewModel
import com.homemade.home.HomeActivity
import com.homemade.login.LoginActivity
import com.homemade.login.R
import com.homemade.login.databinding.FragmentSignInBinding
import com.homemade.login.signup.FirstStepRegistration
import com.homemade.store.StoreActivity
import com.test.utils.*
import com.test.utils.Bases.BaseFragment
import com.test.utils.Ext.saveObject
import com.test.utils.Ext.showToast
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by viewModel()
    lateinit var binding: FragmentSignInBinding
    private val sharePrefrence: SharedPreferences.Editor by inject()
    lateinit var phoneNumber: String
    lateinit var codeNeeded: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as LoginActivity).showSkip()


        loginViewModel.SignInResponse.observe(viewLifecycleOwner, Observer {
            sharePrefrence.putString(TOKEN_USER, it.meta.accessToken).commit()
            sharePrefrence.putBoolean(USER_ISVERIFIED, it.signInResponseItem.verified).commit()
            sharePrefrence.putInt(WALLET_BALANCE, it.signInResponseItem.walletBalance.toInt())
                .commit()

            with(it.signInResponseItem) {
                requireContext().saveObject(
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

            if (!it.signInResponseItem.verified) {
                loginViewModel.resendVerification()
            } else
                //requireActivity().showToast("Success")
                NavigationUtils.goToDestination(requireContext(), HomeActivity::class.java)

        })

        loginViewModel.SuccessSendingVerification.observe(viewLifecycleOwner, Observer {
            dismissAnyDialog()
            showVerificationDialog(loginViewModel, VERIFY_NEW_USER,view = viewLifecycleOwner) { code ->
                codeNeeded = code
            }
        })

        loginViewModel.SuccessVerification.observe(viewLifecycleOwner, Observer {
            sharePrefrence.putBoolean(USER_ISVERIFIED, true).commit()
            showSuccessVerification(function = {
                NavigationUtils.goToDestination(requireContext(), HomeActivity::class.java)
            })
        })


        loginViewModel.ErrorLogin.observe(viewLifecycleOwner, Observer {
            if (it.isBlocked == true) {
                it.message?.let { it1 -> showBlockDialg(it1) }
            } else showErrorDialog(resources.getString(R.string.wrong_phone_or_password))

        })
        loginViewModel.Error.observe(viewLifecycleOwner, {
            showErrorDialog(it)
        })


        loginViewModel.ForgetPasswordResponse.observe(viewLifecycleOwner, Observer {
            showVerificationDialog(
                loginViewModel,
                VERIFY_CHANGE_PASSWORD,
                phoneNumber,
                function = { code ->
                    codeNeeded = code
                })
        })
        loginViewModel.SuccessVerificationChangePassword.observe(viewLifecycleOwner, Observer {
            showResetPassoword(function = { newPass, confirmpass ->
                loginViewModel.resetPassword(phoneNumber, codeNeeded, newPass, confirmpass)
            })
        })

        loginViewModel.SuccessResetPassword.observe(viewLifecycleOwner, Observer {
            //  requireActivity().showToast(it.message)
            dismissAnyDialog()
            showSuccessResetPassword()
        })

        binding.btnCreateAccount.setOnClickListener {
            startActivity(Intent(requireContext(), StoreActivity::class.java))
            //startActivity(Intent(requireContext(), FirstStepRegistration::class.java))
        }

        binding.tvForget.setOnClickListener {
            showForgetPasswordDialog(loginViewModel) { phoneNumber ->
                this.phoneNumber = phoneNumber
            }
        }

        binding.btnSignIn.setOnClickListener {
            if (checkValidation(custom_telephone.editText)) {
                if (checkValidation(custom_password.editText)) {
                    loginViewModel.signIn(
                        dialCode = "+966",
                        phone = binding.customTelephone.editText.text.toString(),
                        password = binding.customPassword.editText.text.toString()
                    )
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearAll()
    }

    private fun checkValidation(
        editTextField: EditText? = null,
        message: String? = null
    ): Boolean {
        if (editTextField?.text?.isEmpty()!!) {
            editTextField.error = resources.getString(R.string.checkInput)
            return false
        }
        return true

    }
}



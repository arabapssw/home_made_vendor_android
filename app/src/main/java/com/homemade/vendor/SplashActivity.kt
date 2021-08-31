package com.homemade.vendor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.lifecycle.Observer
import com.floriaapp.core.ui.SplashViewModel
import com.homemade.home.HomeActivity
import com.homemade.login.LoginActivity
import com.homemade.onboarding.OnBoardingActivity
import com.test.utils.*
import com.test.utils.Bases.BaseActivity
import com.test.utils.Ext.saveList
import com.test.utils.Ext.saveObject
import com.test.utils.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity() {

    private val splashViewModel: SplashViewModel by viewModel()
    lateinit var imageView: ImageView
    lateinit var welcome: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.homemade.vendor.R.layout.activity_splash)
        imageView = findViewById(R.id.iv_logo)
        welcome = findViewById(com.homemade.vendor.R.id.tv_welcome)


//        splashViewModel.getGeneralData()
//        splashViewModel.GeneralData.observe(this, Observer {
//            saveList(COUNTRIES,it.data.countries)
//            saveList(NATIONALITIES,it.data.nationalities)
//            saveObject(BANK,it.data.bankTransferSettings)
//            saveObject(SOCIAL_MEDIA_SETTINGS,it.data.settings)
//
//            GlobalScope.launch {
//                delay(SPLASH_DELAY)
//                animateView(imageView)
//
//            }
//        })


        GlobalScope.launch {
            delay(SPLASH_DELAY)
            animateView(imageView)

        }


    }

    private fun animateView(imageView: ImageView?) {

        val animSlide = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.anim
        )
        imageView?.startAnimation(animSlide)
        animSlide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationRepeat(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                val fadeIn = AlphaAnimation(0f, 1f)
                fadeIn.interpolator = DecelerateInterpolator() //add this
                fadeIn.duration = 1000
                welcome.animation = fadeIn
                welcome.visibility = View.VISIBLE
                fadeIn.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        val isLoggedIn = getSharedPrefrenceInstance().getString(TOKEN_USER, null)
                        val isVerified =
                            getSharedPrefrenceInstance().getBoolean(USER_ISVERIFIED, false)

                        val isPassedOnBoarding = getSharedPrefrenceInstance().getBoolean(
                            IS_PASSED_BY_ONBOARDING, false
                        )

                        if (isLoggedIn != null && isVerified) {
                            NavigationUtils.goToDestination(
                                this@SplashActivity,
                                HomeActivity::class.java
                            )
                        } else {
                            if (!isPassedOnBoarding)

                                startActivity(
                                    Intent(
                                        this@SplashActivity,
                                        OnBoardingActivity::class.java
                                    )
                                )
                            else startActivity(
                                Intent(
                                    this@SplashActivity,
                                    LoginActivity::class.java
                                )
                            )
                        }
                        finish()
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }

                })
            }
        })


    }
}
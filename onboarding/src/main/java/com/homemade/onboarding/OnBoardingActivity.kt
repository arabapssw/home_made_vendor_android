package com.homemade.onboarding

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.homemade.home.HomeActivity
import com.homemade.login.LoginActivity
import com.homemade.onboarding.adapter.IntroAdapter
import com.homemade.onboarding.databinding.ActivityOnBoardingBinding
import com.test.utils.Bases.BaseActivity
import com.test.utils.IS_PASSED_BY_ONBOARDING
import kotlinx.android.synthetic.main.activity_on_boarding.*
import org.koin.android.ext.android.inject
import java.util.*

class OnBoardingActivity : BaseActivity() {
    lateinit var introAdapter: IntroAdapter
    lateinit var binding: ActivityOnBoardingBinding
    var initialItemToBeScrolled = 1
    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 500 //delay in milliseconds before task is to be executed
    val PERIOD_MS: Long = 3000 // time in milliseconds between successive task executions.


    private val sharePrefrence: SharedPreferences.Editor by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR;

        sharePrefrence.putBoolean(IS_PASSED_BY_ONBOARDING, true).commit()

        introAdapter = IntroAdapter(this)
        binding.viewPager.adapter = introAdapter
        binding.viewPager.beginFakeDrag()
        binding.dotsIndicator.setViewPager(viewPager = binding.viewPager)

        binding.tvSkip.setOnClickListener {
            startActivity(Intent(this@OnBoardingActivity, LoginActivity::class.java))
            finish()
        }

        binding.btnNext.setOnClickListener {
            Log.i("data", initialItemToBeScrolled.toString())
            if (initialItemToBeScrolled < introAdapter.count) {
                view_pager.currentItem++
                initialItemToBeScrolled++
            } else {
                startActivity(Intent(this@OnBoardingActivity, LoginActivity::class.java))
                finish()
            }
        }

//        val handler = Handler()
//        val Update = Runnable {
//            if (currentPage == 3) {
//                currentPage = 0
//            }
//            binding.viewPager.setCurrentItem(currentPage++, true)
//        }
//
//        timer = Timer() // This will create a new Thread
//
//        timer?.schedule(object : TimerTask() {
//            // task to be scheduled
//            override fun run() {
//                handler.post(Update)
//            }
//        }, DELAY_MS, PERIOD_MS)


    }
}
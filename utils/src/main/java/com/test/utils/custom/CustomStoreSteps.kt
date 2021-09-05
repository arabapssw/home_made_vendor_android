package com.test.utils.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.floriaapp.core.domain.model.checkout.order.order_details.OrderDetailsResponse
import com.floriaapp.core.domain.model.checkout.order.order_details.orderTracking
import com.test.utils.*

class CustomStoreSteps(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.store_steps, this)
    }

    fun setStep(step:Int) {
        when (step) {
            1 -> {
                findViewById<TextView>(R.id.step1).setBackgroundResource(R.drawable.ic_step_active)
                findViewById<TextView>(R.id.tv_store_data).setTextColor(ContextCompat.getColor(context,R.color.teaBlue))
                findViewById<View>(R.id.from_step1_2).setBackgroundResource(R.color.teaBlue)
                findViewById<TextView>(R.id.step2).setBackgroundResource(R.drawable.ic_step)
                findViewById<TextView>(R.id.tv_complete_data).setTextColor(Color.BLACK)
                findViewById<View>(R.id.from_step2_3).setBackgroundResource(R.color.colorStepInactive)
                findViewById<TextView>(R.id.step3).setBackgroundResource(R.drawable.ic_step)
                findViewById<TextView>(R.id.tv_payment_data).setTextColor(Color.BLACK)
                findViewById<View>(R.id.from_step3_4).setBackgroundResource(R.color.colorStepInactive)
                findViewById<TextView>(R.id.step4).setBackgroundResource(R.drawable.ic_done)
                findViewById<TextView>(R.id.tv_created_data).setTextColor(Color.BLACK)
            }
            2 ->{
                findViewById<TextView>(R.id.step1).setBackgroundResource(R.drawable.ic_step_active)
                findViewById<TextView>(R.id.tv_store_data).setTextColor(Color.BLACK)
                findViewById<View>(R.id.from_step1_2).setBackgroundResource(R.color.teaBlue)
                findViewById<TextView>(R.id.step2).setBackgroundResource(R.drawable.ic_step_active)
                findViewById<TextView>(R.id.tv_complete_data).setTextColor(ContextCompat.getColor(context,R.color.teaBlue))
                findViewById<View>(R.id.from_step2_3).setBackgroundResource(R.color.teaBlue)
                findViewById<TextView>(R.id.step3).setBackgroundResource(R.drawable.ic_step)
                findViewById<TextView>(R.id.tv_payment_data).setTextColor(Color.BLACK)
                findViewById<View>(R.id.from_step3_4).setBackgroundResource(R.color.colorStepInactive)
                findViewById<TextView>(R.id.step4).setBackgroundResource(R.drawable.ic_done)
                findViewById<TextView>(R.id.tv_created_data).setTextColor(Color.BLACK)
            }
            3 ->{
                findViewById<TextView>(R.id.step1).setBackgroundResource(R.drawable.ic_step_active)
                findViewById<TextView>(R.id.tv_store_data).setTextColor(Color.BLACK)
                findViewById<View>(R.id.from_step1_2).setBackgroundResource(R.color.teaBlue)
                findViewById<TextView>(R.id.step2).setBackgroundResource(R.drawable.ic_step_active)
                findViewById<TextView>(R.id.tv_complete_data).setTextColor(Color.BLACK)
                findViewById<View>(R.id.from_step2_3).setBackgroundResource(R.color.teaBlue)
                findViewById<TextView>(R.id.step3).setBackgroundResource(R.drawable.ic_step_active)
                findViewById<TextView>(R.id.tv_payment_data).setTextColor(ContextCompat.getColor(context,R.color.teaBlue))
                findViewById<View>(R.id.from_step3_4).setBackgroundResource(R.color.teaBlue)
                findViewById<TextView>(R.id.step4).setBackgroundResource(R.drawable.ic_done)
                findViewById<TextView>(R.id.tv_created_data).setTextColor(Color.BLACK)
            }
            4 ->{
                findViewById<TextView>(R.id.step1).setBackgroundResource(R.drawable.ic_step_active)
                findViewById<TextView>(R.id.tv_store_data).setTextColor(Color.BLACK)
                findViewById<View>(R.id.from_step1_2).setBackgroundResource(R.color.teaBlue)
                findViewById<TextView>(R.id.step2).setBackgroundResource(R.drawable.ic_step_active)
                findViewById<TextView>(R.id.tv_complete_data).setTextColor(Color.BLACK)
                findViewById<View>(R.id.from_step2_3).setBackgroundResource(R.color.teaBlue)
                findViewById<TextView>(R.id.step3).setBackgroundResource(R.drawable.ic_step_active)
                findViewById<TextView>(R.id.tv_payment_data).setTextColor(Color.BLACK)
                findViewById<View>(R.id.from_step3_4).setBackgroundResource(R.color.teaBlue)
                findViewById<TextView>(R.id.step4).setBackgroundResource(R.drawable.ic_done_active)
                findViewById<TextView>(R.id.tv_created_data).setTextColor(ContextCompat.getColor(context,R.color.teaBlue))
            }

        }

    }

}
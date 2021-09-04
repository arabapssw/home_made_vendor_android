package com.test.utils.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.test.utils.R

class CustomSwitch(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    //var imageView:ImageView
    init {
        inflate(context, R.layout.custom_switch, this)
        //   imageView = findViewById(R.id.iv_upload)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.edit_text_box)

        findViewById<TextView>(R.id.tv_status).text = attributes.getString(R.styleable.edit_text_box_android_text)
    }



}
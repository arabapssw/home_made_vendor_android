package com.test.utils.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.test.utils.R

class CustomSwitch(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    var switchCompat:SwitchCompat
    init {
        inflate(context, R.layout.custom_switch, this)
        //   imageView = findViewById(R.id.iv_upload)
        switchCompat = findViewById(R.id.switch_compat)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.edit_text_box)

        findViewById<TextView>(R.id.tv_status).text = attributes.getString(R.styleable.edit_text_box_android_text)
    }



}
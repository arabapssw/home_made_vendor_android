package com.test.utils.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.test.utils.R


class CustomSpinner(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

     var spinner: Spinner
    init {
        inflate(context, R.layout.custom_spinner, this)
        spinner = findViewById(R.id.spinner)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.edit_text_box)
        findViewById<TextView>(R.id.tv_type_spinner).text = attributes.getString(R.styleable.edit_text_box_android_text)

    }



}
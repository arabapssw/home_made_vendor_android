package com.test.utils.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.test.utils.R

class CustomUploadBtn(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    var uploadText:TextView
    init {
        inflate(context, R.layout.custom_upload_btn, this)
        uploadText = findViewById(R.id.tv_upload_text)
    }



}
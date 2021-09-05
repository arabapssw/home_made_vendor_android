package com.test.utils.custom

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.text.InputFilter
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.test.utils.LOGIN_CLASS_NAME
import com.test.utils.NAME_SPACE
import com.test.utils.R
import kotlin.math.roundToInt

@SuppressLint("ClickableViewAccessibility")
class CustomAddPhoto(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    init {
        inflate(context, R.layout.custom_add_photo, this)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.add_photo)
        findViewById<TextView>(R.id.tv_item).text = attributes.getString(R.styleable.add_photo_android_text)

    }



}
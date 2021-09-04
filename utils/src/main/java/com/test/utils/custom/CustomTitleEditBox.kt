package com.test.utils.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.*
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.test.utils.NAME_SPACE
import com.test.utils.R
import kotlin.math.roundToInt


@SuppressLint("ClickableViewAccessibility")
class CustomTitleEditBox(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    var editText: EditText

    init {
        inflate(context, R.layout.custom_title_edit_box, this)
        editText = findViewById(R.id.ed_custom)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.edit_text_box)
        val inputType: Int = attributes.getInt(R.styleable.edit_text_box_android_inputType, EditorInfo.TYPE_NULL)
        val maxLength: Int = attrs.getAttributeIntValue(NAME_SPACE, "maxLength", 50)
        val isDescription = attributes.getBoolean(R.styleable.edit_text_box_isDescription, false)
        val isHtml = attributes.getBoolean(R.styleable.edit_text_box_isHtml, false)

        val hint = attributes.getString(R.styleable.edit_Text_android_hint)
        hint?.let {
            editText.hint = it
        }
        when(isHtml){
            true->{
                val builder = SpannableStringBuilder()
                val text = attributes.getString(R.styleable.edit_text_box_android_text)
                val position = text?.indexOf("(",ignoreCase = true,startIndex = 0)
                val redSpannable = SpannableString(text)
                    redSpannable.setSpan(ForegroundColorSpan(resources.getColor(R.color.gray178)),
                        position!!, text.length, 0)

                builder.append(redSpannable);
                findViewById<TextView>(R.id.tv_item).text = builder

            }
            false->
                findViewById<TextView>(R.id.tv_item).text = attributes.getString(R.styleable.edit_text_box_android_text)

        }
        val isPassword :Boolean = attributes.getBoolean(R.styleable.edit_text_box_isPassword,false)
        if (isPassword){
            findViewById<ImageView>(R.id.iv_password_toogle).visibility = View.VISIBLE
        }else findViewById<ImageView>(R.id.iv_password_toogle).visibility = View.GONE
        if (isDescription) {
            val inPixels = context.resources.getDimension(R.dimen.dimen_entry_in_dp);
            editText.layoutParams.height = inPixels.roundToInt()
        }
        inputType.let { editText.inputType = it }
        editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))

        var isHidden = true
        findViewById<ImageView>(R.id.iv_password_toogle).setOnClickListener {
            if (isHidden){
                findViewById<ImageView>(R.id.iv_password_toogle).setImageResource(R.drawable.ic_open_eye)
                if(inputType == 129)editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                isHidden = false
            }else{
                findViewById<ImageView>(R.id.iv_password_toogle).setImageResource(R.drawable.ic_icon_eye)
                if(inputType == 129)editText.transformationMethod = PasswordTransformationMethod.getInstance()
                isHidden = true
            }
        }

        editText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) findViewById<LinearLayout>(R.id.box_linear).background = ContextCompat.getDrawable(context,R.drawable.box_tea_blue_rectangle)
            else findViewById<LinearLayout>(R.id.box_linear).background = ContextCompat.getDrawable(context,R.drawable.box_edit_text)
        }



    }

    fun getText(): String {
        return editText.text.trim().toString()
    }

}
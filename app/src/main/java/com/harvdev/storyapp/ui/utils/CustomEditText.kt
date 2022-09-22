package com.harvdev.storyapp.ui.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.setPadding
import com.harvdev.storyapp.R

class CustomEditText : AppCompatEditText {

    private lateinit var clearButtonImage: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    fun setInputTypePassword(){
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        transformationMethod = PasswordTransformationMethod.getInstance()
    }

    fun isNotEmpty() : Boolean {
        return text.toString() != ""
    }

    fun isNotEmptyAndError() : Boolean {
        return error == null && text.toString() != ""
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length < 6 && inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) setError() else setNotError()
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.

            }
        })

        setBackgroundResource(R.drawable.bg_custom_edit_text)
        setPadding(24)
    }

    private fun setNotError(){
        setBackgroundResource(R.drawable.bg_custom_edit_text)
        setPadding(24)
    }

    private fun setError(){
        setError("Harus lebih dari 6 karakter")
        setBackgroundResource(R.drawable.bg_custom_edit_text_error)
        setPadding(24)
    }

}
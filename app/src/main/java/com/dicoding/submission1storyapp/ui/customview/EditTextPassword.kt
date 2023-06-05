package com.dicoding.submission1storyapp.ui.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.submission1storyapp.R

class EditTextPassword : AppCompatEditText {
    private lateinit var iconPassword: Drawable

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        iconPassword = ContextCompat.getDrawable(context, R.drawable.ic_baseline_password_24) as Drawable
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        compoundDrawablePadding = 32

        setHint("Password")
        setAutofillHints(AUTOFILL_HINT_PASSWORD)
        setIconPassword(iconPassword)
        transformationMethod = PasswordTransformationMethod()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty() && s.length < 8) {
                    error = "Password harus lebih dari 8 karakter!"
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    private fun setIconPassword(start: Drawable? = null, end: Drawable? = null, top: Drawable? = null, bottom: Drawable? = null) {
        setCompoundDrawablesWithIntrinsicBounds(start, end, top, bottom)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(context: Context) : super(context) {
        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
}
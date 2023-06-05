package com.dicoding.submission1storyapp.ui.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.submission1storyapp.R

class EditTextEmail : AppCompatEditText {
    private lateinit var iconEmail: Drawable

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        iconEmail = ContextCompat.getDrawable(context, R.drawable.ic_baseline_email_24) as Drawable
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        compoundDrawablePadding = 32

        setHint("Email")
        setAutofillHints(AUTOFILL_HINT_EMAIL_ADDRESS)
        setEmailIcon(iconEmail)
        importantForAccessibility

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty() && !Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    error = "Email Invalid"
                }
            }

            override fun afterTextChanged(s: Editable) {

            }

        })
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

    private fun setEmailIcon(start: Drawable? = null, end: Drawable? = null, top: Drawable? = null, bottom: Drawable? = null) {
        setCompoundDrawablesWithIntrinsicBounds(start, end, top, bottom)
    }
}
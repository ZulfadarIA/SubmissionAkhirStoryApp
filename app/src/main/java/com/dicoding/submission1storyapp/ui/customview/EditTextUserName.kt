package com.dicoding.submission1storyapp.ui.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.InputType
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.submission1storyapp.R

class EditTextUserName : AppCompatEditText {
    private lateinit var userNameIcon: Drawable

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        userNameIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_person_24) as Drawable
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        compoundDrawablePadding = 32

        setHint("Username")
        setUserNameIcon(userNameIcon)
        importantForAccessibility
    }

    private fun setUserNameIcon(start: Drawable? = null, end: Drawable? = null, top: Drawable? = null, bottom: Drawable? = null) {
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
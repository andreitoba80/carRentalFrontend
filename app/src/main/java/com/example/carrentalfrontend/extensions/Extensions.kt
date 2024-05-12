package com.example.carrentalfrontend.extensions

import android.content.res.ColorStateList
import android.util.Patterns
import android.view.View
import com.example.carrentalfrontend.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

private val iconColorOnFocusChangeListener =
    View.OnFocusChangeListener { view, hasFocus ->
        val color =
            if (hasFocus) view.resources.getColor(R.color.color_primary, null)
            else view.resources.getColor(R.color.color_on_surface, null)

        // TextInputLayout is the grandparent of TextInputEditText
        val parent = view.parent.parent as TextInputLayout
        parent.setStartIconTintList(ColorStateList.valueOf(color))
        parent.setEndIconTintList(ColorStateList.valueOf(color))
    }

fun TextInputEditText.addIconFocusChangeColorEffect() {
    onFocusChangeListener = iconColorOnFocusChangeListener
}

fun TextInputEditText.isEmpty(): Boolean = this.text.toString().trim().isEmpty()

fun TextInputEditText.trimmedText(): String = this.text.toString().trim()

fun TextInputEditText.isValidEmail(): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(this.trimmedText()).matches()

package com.spravochnic.scbguide.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.spravochnic.scbguide.R

fun showSnackBar(
    text: String,
    anchorView: View,
    buttonText: String? = null,
    onButtonClicked: (() -> Unit)? = null,
    lengthLong: Int = Snackbar.LENGTH_SHORT,
    transactionY: Boolean = false
) {
    val imm =
        anchorView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive) imm.hideSoftInputFromWindow(anchorView.windowToken, 0)
    val snackBar = Snackbar.make(
        anchorView, text,
        lengthLong
    )
    val textView =
        snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textView.maxLines = 10
    snackBar.setBackgroundTint(ContextCompat.getColor(anchorView.context, R.color.teal_700))
    snackBar.setTextColor(
        ContextCompat.getColor(
            anchorView.context,
            R.color.white
        )
    )
    snackBar.setActionTextColor(
        ContextCompat.getColor(
            anchorView.context,
            R.color.white
        )
    )
    if (transactionY) {
        val transactionYDimens = 60.dp
        snackBar.view.animate().translationY(-transactionYDimens)
    }
    if (buttonText != null) {
        snackBar.setAction(buttonText) {
            onButtonClicked?.invoke() ?: snackBar.dismiss()
        }
        snackBar.duration = Snackbar.LENGTH_INDEFINITE
    }
    snackBar.show()
}

fun Context.showToast(text: String = "Функционал в разработке") {
    val toast = Toast.makeText(
        this,
        text, Toast.LENGTH_SHORT
    )
    toast.show()
}
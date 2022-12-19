package com.spravochnic.scbguide.utils

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.spravochnic.scbguide.R

inline fun Fragment.showAlert(
    text: String?,
    positiveBtnText: String,
    negativeBtnText: String,
    crossinline listenerNegative: () -> Unit = {},
    crossinline listenerPositive: () -> Unit = {},
) {
    MaterialAlertDialogBuilder(requireContext())
        .setMessage(text)
        .setCancelable(false)
        .setNegativeButton(negativeBtnText) { dialog, which -> listenerNegative.invoke() }
        .setPositiveButton(positiveBtnText) { dialog, which -> listenerPositive.invoke() }
        .show()
}

inline fun Fragment.showConfirmDialog(
    @StringRes textRes: Int = R.string.alert_message,
    crossinline listener: () -> Unit = {},
) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.alert_warning)
        .setMessage(textRes)
        .setNegativeButton(R.string.alert_cancel, null)
        .setPositiveButton(R.string.alert_accept) { _, _ -> listener() }
        .show()
}
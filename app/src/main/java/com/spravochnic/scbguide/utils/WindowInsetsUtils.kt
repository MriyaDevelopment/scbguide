package com.spravochnic.scbguide.utils

import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun WindowInsetsControllerCompat.hideKeyboard() {
    hide(WindowInsetsCompat.Type.ime())
}

fun WindowInsetsControllerCompat.showKeyboard() {
    show(WindowInsetsCompat.Type.ime())
}
package com.spravochnic.scbguide.utils

import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import javax.inject.Inject

class StoragePickerUtils @Inject constructor(fragment: Fragment) {

    private var action: ((Uri) -> Unit)? = null

    private val galleryResultLauncher =
        fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val uri = it.data?.data ?: return@registerForActivityResult
            action?.invoke(uri)
        }


    fun galleryImagePicker(vararg types: String = arrayOf("image/*"), action: (Uri) -> Unit) {
        this.action = action

        val intent = Intent(Intent.ACTION_PICK)

        intent.type = ("image/png|image/jpg|image/jpeg")

        intent.putExtra(Intent.EXTRA_MIME_TYPES, types)
        galleryResultLauncher.launch(intent)
    }
}
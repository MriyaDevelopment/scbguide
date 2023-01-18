package com.spravochnic.scbguide.utils

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import java.io.File

fun Uri.getFile(contentResolver: ContentResolver): File? {
    val path = this.getPath(contentResolver) ?: return null
    return File(path)
}

fun Uri.getPath(contentResolver: ContentResolver): String? {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor: Cursor =
        contentResolver.query(this, projection, null, null, null) ?: return null
    val index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor.moveToFirst()
    val path: String = cursor.getString(index)
    cursor.close()
    return path
}
package com.example.carrentalfrontend.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.example.carrentalfronted.Person
import java.io.ByteArrayOutputStream

fun Person.isEmpty(): Boolean {
    return username.isBlank() && email.isBlank() && fullName.isBlank()
}

fun Person.toStringV2(): String {
    return "Person [id = $id, username = $username, email = $email, fullName = $fullName, isAdmin = $isAdmin]"
}

fun Any.logDebugError(message: String) {
    Log.e("TOBA ${this::class.java.simpleName}", message)
}

/**
 * Provides the corrected orientation version of a bitmap image.
 *
 * @param uri the URI of the image
 * @param activity the calling activity
 * @return the corrected orientation bitmap image as [Bitmap]
 */
fun getCorrectedBitmap(uri: Uri, activity: FragmentActivity?): Bitmap? {
    return BitmapFactory.decodeStream(activity?.contentResolver?.openInputStream(uri))?.let {
        activity?.let { it1 -> correctRotationBitmap(it, uri, it1) }
    }
}

/**
 * Default Android photo picker rotates the image on some phone models. This function checks the
 * exif information and in case a wrong rotation is detected, it corrects it.
 */
private fun correctRotationBitmap(img: Bitmap, uri: Uri, activity: FragmentActivity): Bitmap {
    val mediaDataCursor = activity.contentResolver?.query(
        uri,
        arrayOf(MediaStore.Images.Media.DATA),
        null,
        null,
        null
    ) ?: return img

    mediaDataCursor.moveToFirst()
    val orientation: Float =
        when (ExifInterface(mediaDataCursor.getString(0)!!).getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90f
            ExifInterface.ORIENTATION_ROTATE_180 -> 180f
            ExifInterface.ORIENTATION_ROTATE_270 -> 270f
            else -> 0f
        }

    mediaDataCursor.close()

    val mat: Matrix? = Matrix()
    mat?.postRotate(orientation)
    return Bitmap.createBitmap(img, 0, 0, img.width, img.height, mat, true)
}

fun Bitmap.toByteArray(): ByteArray {
    ByteArrayOutputStream().apply {
        compress(Bitmap.CompressFormat.JPEG, 100, this)
        return toByteArray()
    }
}
package com.voiceeffects.supereffect.convertvideotomp3.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment


inline fun <FRAGMENT : Fragment> FRAGMENT.putArgs(
    argsBuilder: Bundle.() -> Unit
): FRAGMENT = this.apply { arguments = Bundle().apply(argsBuilder) }

fun View.preventTwoClick(time: Int) {
    try {
        if (isAttachedToWindow) {
            isEnabled = false
            isClickable = false
            isFocusable = false
            postDelayed({
                isEnabled = true
                isClickable = true
                isFocusable = true
            }, time.toLong())
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.getFontWidthId(id: Int) = ResourcesCompat.getFont(this, id)
fun Context.isGrantAudio() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        ContextCompat.checkSelfPermission(
            this,
            if (Build.VERSION.SDK_INT < 33) Manifest.permission.READ_EXTERNAL_STORAGE else Manifest.permission.READ_MEDIA_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    else
        ContextCompat.checkSelfPermission(
            this,
            if (Build.VERSION.SDK_INT < 33) Manifest.permission.READ_EXTERNAL_STORAGE else Manifest.permission.READ_MEDIA_AUDIO
        ) == PackageManager.PERMISSION_GRANTED


fun Context.isGrantNotificationPermission() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }


fun Activity.hideKeyboard() {
    if (currentFocus != null) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            currentFocus!!.windowToken,
            0
        )
    }
}

fun View.visible() = if (!this.isVisible) this.visibility = View.VISIBLE else {
}

fun View.gone() = if (this.isVisible) this.visibility = View.GONE else {
}

fun View.invisible() = if (this.isVisible) this.visibility = View.INVISIBLE else {
}


fun Activity.getDrawableWithId(id: Int) =
    ResourcesCompat.getDrawable(this.resources, id, null)


package com.voiceeffects.supereffect.convertvideotomp3.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileDownloadModel(
    val path: String?,
    val fileName: String?,
    val resourceId: String?,
    val postId: String?,
    var filePath: String? = null,
    var isCancel: Boolean = false
): Parcelable
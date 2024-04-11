package com.voiceeffects.supereffect.convertvideotomp3.data.model

data class VideoModel(
    var id: Long = 0,
    var title: String = "",
    var duration: Long = 5000,
    var path: String = "",
    var size: Long = 0,
    var artist: String="",
    var album: String=""
)
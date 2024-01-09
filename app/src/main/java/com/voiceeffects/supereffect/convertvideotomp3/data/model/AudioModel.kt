package com.voiceeffects.supereffect.convertvideotomp3.data.model

data class AudioModel(
    var nameFile: String,
    var size: Long,
    var date: Long,
    var path: String,
    var durationAudio: Long,
    var bitrate: Int,
    var artist: String,
    var album: String,
    var genre: String
) {
    companion object {
        const val MODE_CBR = "CBR"
        const val MODE_VBR = "VBR"
        const val BITRATE_CBR_32 = 32
        const val BITRATE_CBR_64 = 64
        const val BITRATE_CBR_128 = 128
        const val BITRATE_CBR_192 = 192
        const val BITRATE_CBR_256 = 256
        const val BITRATE_CBR_320 = 320
        const val BITRATE_VBR_130 = 130
        const val BITRATE_VBR_195 = 190
        const val BITRATE_VBR_245 = 245
        const val TIME_NONE_CUT = -1L
    }
}
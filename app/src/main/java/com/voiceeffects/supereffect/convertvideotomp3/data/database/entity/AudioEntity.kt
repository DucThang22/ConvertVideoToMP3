package com.voiceeffects.supereffect.convertvideotomp3.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.AudioEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class AudioEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = ID) var id: Int,

    @ColumnInfo(name = NAME_FILE) var nameFile: String,

    @ColumnInfo(name = SIZE) var size: Long,

    @ColumnInfo(name = DATE) var date: Long,

    @ColumnInfo(name = FORMAT) var format: String,

    @ColumnInfo(name = PATH_SAVE) var pathSave: String,

    @ColumnInfo(name = PATH_PLAY) var pathPlay: String,

    @ColumnInfo(name = DURATION_AUDIO) var durationAudio: Long,

    @ColumnInfo(name = BITRATE) var bitrate: Int,

    @ColumnInfo(name = TYPE_BITRATE) var typeBitrate: String,

    @ColumnInfo(name = FREQUENCY) var frequency: Int,

    @ColumnInfo(name = TITLE) var title: String,

    @ColumnInfo(name = ARTIST) var artist: String,

    @ColumnInfo(name = ALBUM) var album: String,

    @ColumnInfo(name = GENRE) var genre: String,

    @ColumnInfo(name = START) var start: Long,

    @ColumnInfo(name = END) var end: Long,

    @ColumnInfo(name = IS_PLAY) var isPlay: Boolean
) {
    companion object {
        const val TABLE_NAME = "audio_table"
        const val ID = "id"
        const val NAME_FILE = "nameFile"
        const val SIZE = "size"
        const val DATE = "date"
        const val FORMAT = "format"
        const val DURATION_AUDIO = "durationAudio"
        const val BITRATE = "bitrate"
        const val TYPE_BITRATE = "typeBitrate"
        const val FREQUENCY = "frequency"
        const val GENRE = "genre"
        const val START = "start"
        const val END = "end"
        const val VIDEO_GALLERY = "VideoGallery"
        const val PATH_SAVE = "pathSave"
        const val PATH_PLAY = "pathPlay"
        const val TITLE = "title"
        const val ARTIST = "artist"
        const val ALBUM = "album"
        const val IS_PLAY = "isPlay"
    }
}
package com.voiceeffects.supereffect.convertvideotomp3.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.VideoGalleryEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class VideoGalleryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    var id: Long = 0,

    @ColumnInfo(name = TITLE)
    var title: String,

    @ColumnInfo(name = DURATION)
    var duration: Long,

    @ColumnInfo(name = PATH)
    var path: String,

    @ColumnInfo(name = SIZE)
    var size: Long,

    @ColumnInfo(name = ARTIST)
    var artist: String,

    @ColumnInfo(name = ALBUM)
    var album: String
) {
    companion object {
        const val TABLE_NAME = "video_gallery_table"
        const val ID = "id_video"
        const val TITLE = "title_video"
        const val DURATION = "duration_video"
        const val PATH = "path_video"
        const val SIZE = "size_video"
        const val ARTIST = "artist_video"
        const val ALBUM = "album_video"
    }
}
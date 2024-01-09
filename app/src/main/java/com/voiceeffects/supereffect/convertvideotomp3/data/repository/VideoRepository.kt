package com.voiceeffects.supereffect.convertvideotomp3.data.repository

import android.content.Context
import android.provider.MediaStore
import com.voiceeffects.supereffect.convertvideotomp3.data.database.dao.VideoDao
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.VideoGalleryEntity
import com.voiceeffects.supereffect.convertvideotomp3.data.model.VideoModel
import java.io.File
import javax.inject.Inject

class VideoRepository @Inject constructor(
    private val videoDao: VideoDao
) {
    suspend fun addVideo(videoGalleryEntity: VideoGalleryEntity) = videoDao.addVideo(videoGalleryEntity)

    suspend fun deleteVideo(id: Long) = videoDao.deleteVideo(id)

    suspend fun deleteAllVideo() = videoDao.deleteAllVideo()

    suspend fun updateVideo(videoGalleryEntity: VideoGalleryEntity) = videoDao.updateVideo(videoGalleryEntity)

    suspend fun getAllVideo() = videoDao.getAllVideo()

    fun syncVideoFromExternalStorage(context: Context): MutableList<VideoModel> {
        val listVideo: MutableList<VideoModel> = mutableListOf()
        context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Video.VideoColumns.DATA,
                MediaStore.Video.VideoColumns.TITLE,
                MediaStore.Video.VideoColumns.DURATION,
                MediaStore.Video.VideoColumns.SIZE,
                MediaStore.Video.VideoColumns.ARTIST,
                MediaStore.Video.VideoColumns.ALBUM
            ), null, null, null
        )?.use { cursor ->
            val columnPath = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA)
            val columnTitle = cursor.getColumnIndex(MediaStore.Video.VideoColumns.TITLE)
            val columnDuration = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION)
            val columnSize = cursor.getColumnIndex(MediaStore.Video.VideoColumns.SIZE)
            val columnArtist = cursor.getColumnIndex(MediaStore.Video.VideoColumns.ARTIST)
            val columnAlbum = cursor.getColumnIndex(MediaStore.Video.VideoColumns.ALBUM)
            while (cursor.moveToNext()) {
                if (columnPath > -1) {
                    val path = cursor.getString(columnPath)
                    if (path != null && !path.startsWith(".")) {
                        val file = File(path)
                        if (file.isFile && !file.isHidden) {
                            val nameFileVideo = cursor.getString(columnTitle) ?: file.name
                            val nameSize = cursor.getLong(columnSize)
                            val nameDuration = cursor.getLong(columnDuration)
                            val nameArtist = cursor.getString(columnArtist)
                            val nameAlbum = cursor.getString(columnAlbum)
                            if (nameFileVideo.startsWith(".")) continue
                            val duration = cursor.getLong(columnDuration)
                            if (duration >= 1000) {
                                listVideo.add(
                                    VideoModel(
                                        nameFileVideo,
                                        nameDuration,
                                        path,
                                        nameSize,
                                        nameArtist,
                                        nameAlbum
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
        listVideo.sortWith(compareBy { it.title.uppercase() })
        return listVideo
    }
}
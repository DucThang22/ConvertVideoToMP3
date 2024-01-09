package com.voiceeffects.supereffect.convertvideotomp3.data.repository

import com.voiceeffects.supereffect.convertvideotomp3.data.database.dao.VideoDao
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.VideoGalleryEntity
import javax.inject.Inject

class VideoRepository @Inject constructor(
    private val videoDao: VideoDao
) {
    suspend fun addVideo(videoGalleryEntity: VideoGalleryEntity) = videoDao.addVideo(videoGalleryEntity)

    suspend fun deleteVideo(id: Long) = videoDao.deleteVideo(id)

    suspend fun deleteAllVideo() = videoDao.deleteAllVideo()

    suspend fun updateVideo(videoGalleryEntity: VideoGalleryEntity) = videoDao.updateVideo(videoGalleryEntity)

    suspend fun getAllVideo() = videoDao.getAllVideo()
}
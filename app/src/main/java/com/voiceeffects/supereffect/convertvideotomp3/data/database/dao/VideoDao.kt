package com.voiceeffects.supereffect.convertvideotomp3.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.VideoGalleryEntity

@Dao
interface VideoDao {
    @Insert
    suspend fun addVideo(videoGalleryEntity: VideoGalleryEntity)

    @Update
    suspend fun updateVideo(videoGalleryEntity: VideoGalleryEntity)

    @Query("Delete  From VIDEO_GALLERY_TABLE where id_video = :id")
    suspend fun deleteVideo(id: Long)

    @Query("DELETE FROM VIDEO_GALLERY_TABLE")
    suspend fun deleteAllVideo()

    @Query("SELECT * FROM ${VideoGalleryEntity.TABLE_NAME}")
    suspend fun getAllVideo(): List<VideoGalleryEntity>

//    @Query("SELECT * FROM ${VideoGalleryEntity.TABLE_NAME} WHERE ${VideoGalleryEntity.ID} = :id")
//    suspend fun getAudioById(id: Int): VideoGalleryEntity?
//
//    @Query("SELECT COUNT(*) FROM ${AudioEntity.TABLE_NAME} WHERE ${VideoGalleryEntity.NAME_FILE} = :name")
//    suspend fun getAudioCountByName(name: String): Int
}
package com.voiceeffects.supereffect.convertvideotomp3.data.repository

import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.voiceeffects.supereffect.convertvideotomp3.data.database.dao.AudioDao
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.AudioEntity
import com.voiceeffects.supereffect.convertvideotomp3.data.model.AudioModel
import com.voiceeffects.supereffect.convertvideotomp3.data.model.VideoModel
import com.voiceeffects.supereffect.convertvideotomp3.utils.DataState
import com.voiceeffects.supereffect.convertvideotomp3.utils.SystemUtil.getAllNameInFolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class AudioRepository @Inject constructor(
    private val audioDao: AudioDao
) {
    suspend fun addAudio(audioEntity: AudioEntity) = audioDao.addAudio(audioEntity)

    suspend fun updateAudio(audioEntity: AudioEntity) = audioDao.updateAudio(audioEntity)

    suspend fun deleteAudio(id: Long) = audioDao.deleteAudio(id)

    suspend fun deleteAllAudio() = audioDao.deleteAllAudio()

    suspend fun getAllAudio() = audioDao.getAllAudio()

    suspend fun getAudioById(id: Int) = audioDao.getAudioById(id)

    suspend fun getAudioCountByName(name: String) = audioDao.getAudioCountByName(name)

    fun fromDomain(domainModel: AudioModel): AudioEntity {
        return AudioEntity(
            id = domainModel.id,
            nameFile = domainModel.nameFile,
            size = domainModel.size,
            date = domainModel.date,
            format = domainModel.format,
            pathSave = domainModel.pathSave,
            pathPlay = domainModel.pathPlay,
            durationAudio = domainModel.durationAudio,
            bitrate = domainModel.bitrate,
            frequency = domainModel.frequency,
            genre = domainModel.genre,
            title = domainModel.title,
            album = domainModel.album,
            artist = domainModel.artist,
            start = domainModel.start,
            end = domainModel.end,
            typeBitrate = domainModel.typeBitrate,
            isPlay = false
        )
    }

    fun addAudioVideoGallery(videoGallery: VideoModel): Flow<DataState<AudioModel>> =
        flow {
            emit(DataState.Loading())

            val audioModel = AudioModel(
                id = 0,
                nameFile = videoGallery.title,
                size = videoGallery.size,
                date = System.currentTimeMillis(),
                format = "Mp3",
                pathSave = "",
                pathPlay = "",
                durationAudio = videoGallery.duration,
                bitrate = 128,
                frequency = 0,
                title = videoGallery.title,
                artist = videoGallery.artist,
                album = videoGallery.album,
                genre = "",
                start = 0L,
                end = videoGallery.duration,
                typeBitrate = "CBR"
            )

            audioModel.nameFile = getAllNameInFolder(
                audioModel.nameFile,
                audioModel.format
            )
        }
}
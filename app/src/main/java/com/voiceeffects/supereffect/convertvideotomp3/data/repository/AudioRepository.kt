package com.voiceeffects.supereffect.convertvideotomp3.data.repository

import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.voiceeffects.supereffect.convertvideotomp3.data.database.dao.AudioDao
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.AudioEntity
import com.voiceeffects.supereffect.convertvideotomp3.data.model.AudioModel
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


    @RequiresApi(Build.VERSION_CODES.R)
    fun syncAudioFromExternalStorage(context: Context) : MutableList<AudioModel> {
        val listAudio: MutableList<AudioModel> = mutableListOf()
        context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.ARTIST,
                MediaStore.Audio.AudioColumns.BITRATE,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DATE_ADDED,
                MediaStore.Audio.AudioColumns.DURATION,
                MediaStore.Audio.AudioColumns.SIZE,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.GENRE
            ), null, null, null
        )?.use { cursor ->
            val columnPath = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)
            val columnArtist = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)
            val columnBitrate = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.BITRATE)
            val columnTitle = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE)
            val columnDate = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATE_ADDED)
            val columnDuration = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)
            val columnSize = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.SIZE)
            val columnAlbum = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)
            val columnGenre = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.GENRE)
            while (cursor.moveToNext()) {
                if (columnPath > -1) {
                    val path = cursor.getString(columnPath)
                    if (path != null && !path.startsWith(".")) {
                        val file = File(path)
                        if (file.isFile && !file.isHidden) {
                            val nameFileAudio = cursor.getString(columnTitle) ?: file.name
                            val sizeAudio = cursor.getLong(columnSize)
                            val dateAudio = cursor.getLong(columnDate)
                            val durationAudio = cursor.getLong(columnDuration)
                            val bitrateAudio = cursor.getInt(columnBitrate)
                            val artistAudio = cursor.getString(columnArtist)
                            val albumAudio = cursor.getString(columnAlbum)
                            val genreAudio = cursor.getString(columnGenre)
                            if (nameFileAudio.startsWith(".")) continue
                            val duration = cursor.getLong(columnDuration)
                            if (duration >= 1000) {
                                listAudio.add(
                                    AudioModel(
                                        nameFileAudio,
                                        sizeAudio,
                                        dateAudio,
                                        path,
                                        durationAudio,
                                        bitrateAudio,
                                        artistAudio,
                                        albumAudio,
                                        genreAudio
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
        listAudio.sortWith(compareBy { it.nameFile.uppercase() })
        return listAudio
    }
}
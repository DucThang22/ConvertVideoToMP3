package com.voiceeffects.supereffect.convertvideotomp3.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.AudioEntity
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.AudioEntity.Companion.ID
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.AudioEntity.Companion.NAME_FILE
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.AudioEntity.Companion.TABLE_NAME

@Dao
interface AudioDao {
    @Insert
    suspend fun addAudio(audioEntity: AudioEntity)

    @Update
    suspend fun updateAudio(audioEntity: AudioEntity)

    @Query("Delete  From AUDIO_TABLE where id = :id")
    suspend fun deleteAudio(id: Long)

    @Query("DELETE FROM audio_table")
    suspend fun deleteAllAudio()

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getAllAudio(): List<AudioEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE $ID = :id")
    suspend fun getAudioById(id: Int): AudioEntity?

    @Query("SELECT COUNT(*) FROM $TABLE_NAME WHERE $NAME_FILE = :name")
    suspend fun getAudioCountByName(name: String): Int
}
package com.voiceeffects.supereffect.convertvideotomp3.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.voiceeffects.supereffect.convertvideotomp3.data.database.dao.AudioDao
import com.voiceeffects.supereffect.convertvideotomp3.data.database.dao.VideoDao
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.AudioEntity
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.VideoGalleryEntity

@Database(
    entities = [VideoGalleryEntity::class, AudioEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun videoDao(): VideoDao

    abstract fun audioDao(): AudioDao

    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }

    }
}
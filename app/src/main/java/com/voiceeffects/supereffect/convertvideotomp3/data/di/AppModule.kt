package com.voiceeffects.supereffect.convertvideotomp3.data.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.voiceeffects.supereffect.convertvideotomp3.common.Constants
import com.voiceeffects.supereffect.convertvideotomp3.data.AppDatabase
import com.voiceeffects.supereffect.convertvideotomp3.data.database.dao.AudioDao
import com.voiceeffects.supereffect.convertvideotomp3.data.database.dao.VideoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideSharedPreference(context: Application) : SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideRoomDb3(context: Application): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideVideoDao(db: AppDatabase): VideoDao {
        return db.videoDao()
    }

    @Provides
    @Singleton
    fun provideAudioDao(db: AppDatabase): AudioDao {
        return db.audioDao()
    }

}
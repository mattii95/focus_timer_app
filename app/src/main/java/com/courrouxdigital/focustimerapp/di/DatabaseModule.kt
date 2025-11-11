package com.courrouxdigital.focustimerapp.di

import android.content.Context
import androidx.room.Room
import com.courrouxdigital.focustimerapp.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "focus_timer_database"
        ).build()
    }

    @Singleton
    @Provides
    fun providesTimerSessionDao(db: AppDatabase) = db.timerSessionDao()
}
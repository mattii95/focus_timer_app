package com.courrouxdigital.focustimerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.courrouxdigital.focustimerapp.data.local.dao.TimerSessionDao
import com.courrouxdigital.focustimerapp.data.local.entity.TimerSessionEntity

@Database(entities = [TimerSessionEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun timerSessionDao() : TimerSessionDao
}
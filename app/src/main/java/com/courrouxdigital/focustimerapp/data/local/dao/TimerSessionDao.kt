package com.courrouxdigital.focustimerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.courrouxdigital.focustimerapp.data.local.entity.TimerSessionEntity

@Dao
interface TimerSessionDao {
    @Query("SELECT * FROM timer_session WHERE date LIKE '%' || :date || '%'")
    suspend fun getTimerSessionByDate(date: String): MutableList<TimerSessionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimerSession(timerSessionEntity: TimerSessionEntity): Long
}
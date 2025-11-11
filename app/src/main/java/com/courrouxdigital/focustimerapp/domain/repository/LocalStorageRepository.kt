package com.courrouxdigital.focustimerapp.domain.repository

import com.courrouxdigital.focustimerapp.domain.model.TimerSession

interface LocalStorageRepository {
    suspend fun saveTimerSession(timerSession: TimerSession): Boolean
    suspend fun getTimerSessionByDate(date: String): TimerSession
}
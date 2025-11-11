package com.courrouxdigital.focustimerapp.data.repository

import com.courrouxdigital.focustimerapp.data.local.dao.TimerSessionDao
import com.courrouxdigital.focustimerapp.domain.model.TimerSession
import com.courrouxdigital.focustimerapp.domain.model.toTimerSessionEntity
import com.courrouxdigital.focustimerapp.domain.repository.LocalStorageRepository
import java.lang.Exception
import javax.inject.Inject

class LocalStorageRepositoryImpl @Inject constructor(
    private val timerSessionDao: TimerSessionDao,
) : LocalStorageRepository {
    override suspend fun saveTimerSession(timerSession: TimerSession): Boolean {
        try {
            val result = timerSessionDao.insertTimerSession(timerSession.toTimerSessionEntity())
            return result.toInt() != -1
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTimerSessionByDate(date: String): TimerSession {
        try {
            var timerValue: Long = 0
            var rounds = 0
            timerSessionDao.getTimerSessionByDate(date).map {
                timerValue += it.value
                rounds += 1
            }
            return TimerSession(
                date = date,
                value = timerValue,
                round = rounds
            )
        } catch (e: Exception) {
            throw e
        }
    }
}
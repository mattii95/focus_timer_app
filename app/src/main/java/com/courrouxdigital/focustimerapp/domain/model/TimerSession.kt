package com.courrouxdigital.focustimerapp.domain.model

import com.courrouxdigital.focustimerapp.data.local.entity.TimerSessionEntity

data class TimerSession(
    var date: String,
    var value: Long,
    var round: Int? = 0,
)

fun TimerSession.toTimerSessionEntity(): TimerSessionEntity {
    return TimerSessionEntity(
        date = this.date,
        value = this.value
    )
}

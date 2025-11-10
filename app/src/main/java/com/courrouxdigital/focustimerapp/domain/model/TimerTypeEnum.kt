package com.courrouxdigital.focustimerapp.domain.model

import com.courrouxdigital.focustimerapp.core.Constants

enum class TimerTypeEnum(val title: String, private val time: Long) {
    FOCUS("Focus Time", Constants.FOCUS_TIME),
    SHORT_BREAK("Short Break", Constants.SHORT_BREAK_TIME),
    LONG_BREAK("Long Break", Constants.LONG_BREAK_TIME);

    fun timeToMillis(): Long {
        return time * Constants.ONE_MIN_IN_SEC * Constants.ONE_SEC_IN_MILLIS
    }
}
package com.courrouxdigital.focustimerapp.presentation.home

import android.annotation.SuppressLint
import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.courrouxdigital.focustimerapp.core.Constants
import com.courrouxdigital.focustimerapp.domain.model.Resource
import com.courrouxdigital.focustimerapp.domain.model.TimerSession
import com.courrouxdigital.focustimerapp.domain.model.TimerTypeEnum
import com.courrouxdigital.focustimerapp.domain.usecase.GetTimerSessionByDateUseCase
import com.courrouxdigital.focustimerapp.domain.usecase.SaveTimerSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val saveTimerSessionUseCase: SaveTimerSessionUseCase,
    private val getTimerSessionByDateUseCase: GetTimerSessionByDateUseCase
) : ViewModel() {
    private lateinit var timer: CountDownTimer
    private var isTimerActive: Boolean = false
    private var _sessionTimerValue: Long = 0

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun onStartTimer() {
        viewModelScope.launch {
            val startValue = _uiState.value.timerValue
            timer = object : CountDownTimer(
                startValue,
                Constants.ONE_SEC_IN_MILLIS
            ) {
                override fun onTick(millisUntilFinished: Long) {
                    _uiState.update { it.copy(
                        timerValue = millisUntilFinished,
                        todayTime = it.todayTime + Constants.ONE_SEC_IN_MILLIS
                    ) }
                    _sessionTimerValue +=  Constants.ONE_SEC_IN_MILLIS
                }

                override fun onFinish() {
                    onCancelTimer()
                }
            }
            timer.start().also {
                if (!isTimerActive) _uiState.update { it.copy(rounds = it.rounds + 1) }
                _sessionTimerValue = 0
                isTimerActive = true
            }
        }
    }

    fun onCancelTimer(reset: Boolean = false) {
        try {
            saveTimerSession()
            timer.cancel()
        } catch (_: UninitializedPropertyAccessException) {
            // timer error
        }
        if (!isTimerActive || reset) {
            _uiState.update { it.copy(timerValue = it.timerType.timeToMillis()) }
        }
        isTimerActive = false
    }

    fun onUpdateType(timerType: TimerTypeEnum) {
        _uiState.update { it.copy(timerType = timerType) }
        onCancelTimer(true)
    }

    fun onIncreaseTime() {
        _uiState.update { it.copy(timerValue = it.timerValue + Constants.ONE_MIN_IN_MILLIS) }
        onResetTimer()
    }

    fun onDecreaseTime() {
        _uiState.update { it.copy(timerValue = it.timerValue - Constants.ONE_MIN_IN_MILLIS) }
        onResetTimer()
        if (_uiState.value.timerValue <= 0) {
            onCancelTimer()
        }
    }

    fun getTimerSessionByDate() {
        getTimerSessionByDateUseCase(date = getCurrentDate()).onEach { result ->
            if (result is Resource.Success) {
                _uiState.update { it.copy(
                    rounds = result.data?.round ?: 0,
                    todayTime = result.data?.value ?: 0
                ) }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveTimerSession() {
        val session = TimerSession(
            date = getCurrentDate(),
            value = _sessionTimerValue
        )
        saveTimerSessionUseCase(timerSession = session).onEach { result ->
            when(result) {
                is Resource.Success -> { }
                is Resource.Loading -> { }
                is Resource.Error -> { }
            }
        }.launchIn(viewModelScope)
    }

    private fun onResetTimer() {
        if (isTimerActive) {
            onCancelTimer()
            onStartTimer()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        return dateFormat.format(currentDate)
    }

    @SuppressLint("DefaultLocale")
    fun millisToMinutes(value: Long): String {
        val totalSeconds = value / Constants.ONE_SEC_IN_MILLIS
        val minutes = (totalSeconds / Constants.ONE_MIN_IN_SEC).toInt()
        val seconds = (totalSeconds % Constants.ONE_MIN_IN_SEC).toInt()
        return String.format("%02d:%02d", minutes, seconds)
    }

    @SuppressLint("DefaultLocale")
    fun millisToHours(value: Long): String {
        val totalSeconds = value / Constants.ONE_SEC_IN_MILLIS
        val seconds = (totalSeconds % Constants.ONE_MIN_IN_SEC)
        val totalMinutes = (totalSeconds / Constants.ONE_MIN_IN_SEC).toInt()
        val hours = (totalMinutes / Constants.ONE_HOUR_IN_MIN)
        val minutes = (totalMinutes % Constants.ONE_HOUR_IN_MIN)
        return if (totalMinutes <= Constants.ONE_HOUR_IN_MIN) {
            String.format("%02dm %02ds", minutes, seconds)
        } else {
            String.format("%02dh %02dm", hours, minutes)
        }
    }


    data class UIState(
        val timerValue: Long = TimerTypeEnum.FOCUS.timeToMillis(),
        val timerType: TimerTypeEnum = TimerTypeEnum.FOCUS,
        val rounds: Int = 0,
        val todayTime: Long = 0
    )
}
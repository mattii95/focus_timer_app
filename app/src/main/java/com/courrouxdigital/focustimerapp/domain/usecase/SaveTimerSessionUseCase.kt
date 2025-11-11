package com.courrouxdigital.focustimerapp.domain.usecase

import com.courrouxdigital.focustimerapp.domain.model.Resource
import com.courrouxdigital.focustimerapp.domain.model.TimerSession
import com.courrouxdigital.focustimerapp.domain.repository.LocalStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveTimerSessionUseCase @Inject constructor(
    private val repository: LocalStorageRepository,
) {
    operator fun invoke(timerSession: TimerSession): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            emit(
                Resource.Success(
                    data = repository.saveTimerSession(timerSession)
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error"))
        }
    }
}
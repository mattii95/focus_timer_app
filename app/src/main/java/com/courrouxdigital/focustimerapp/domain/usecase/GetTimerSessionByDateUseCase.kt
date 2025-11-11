package com.courrouxdigital.focustimerapp.domain.usecase

import com.courrouxdigital.focustimerapp.domain.model.Resource
import com.courrouxdigital.focustimerapp.domain.model.TimerSession
import com.courrouxdigital.focustimerapp.domain.repository.LocalStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTimerSessionByDateUseCase @Inject constructor(
    private val repository: LocalStorageRepository,
) {
    operator fun invoke(date: String): Flow<Resource<TimerSession>> = flow {
        try {
            emit(Resource.Loading())
            emit(
                Resource.Success(
                    data = repository.getTimerSessionByDate(date)
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error"))
        }
    }
}
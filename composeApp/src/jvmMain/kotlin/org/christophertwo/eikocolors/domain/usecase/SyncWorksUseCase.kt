package org.christophertwo.eikocolors.domain.usecase

import org.christophertwo.eikocolors.domain.repository.AppRepository

class SyncWorksUseCase(private val repository: AppRepository) {
    suspend operator fun invoke() = repository.syncWorks()
}


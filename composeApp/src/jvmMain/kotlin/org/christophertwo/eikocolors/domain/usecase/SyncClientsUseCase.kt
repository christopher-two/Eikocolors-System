package org.christophertwo.eikocolors.domain.usecase

import org.christophertwo.eikocolors.domain.repository.AppRepository

class SyncClientsUseCase(private val repository: AppRepository) {
    suspend operator fun invoke() = repository.syncClients()
}


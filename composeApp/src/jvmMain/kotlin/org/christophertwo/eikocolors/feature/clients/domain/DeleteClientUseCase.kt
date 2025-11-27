package org.christophertwo.eikocolors.feature.clients.domain

import org.christophertwo.eikocolors.domain.repository.AppRepository

class DeleteClientUseCase(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(id: String) {
        appRepository.deleteClient(id)
    }
}


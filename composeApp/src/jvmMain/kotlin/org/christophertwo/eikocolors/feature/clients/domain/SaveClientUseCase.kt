package org.christophertwo.eikocolors.feature.clients.domain

import org.christophertwo.eikocolors.domain.repository.AppRepository
import org.christophertwo.eikocolors.feature.clients.domain.model.Client

class SaveClientUseCase(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(client: Client) {
        appRepository.saveClient(client)
    }
}


package org.christophertwo.eikocolors.feature.clients.domain

import org.christophertwo.eikocolors.feature.clients.desingsystem.ClientRepository
import org.christophertwo.eikocolors.feature.clients.domain.model.Client

class UpdateClientUseCase(
    private val clientRepository: ClientRepository
) {
    suspend operator fun invoke(client: Client) {
        clientRepository.updateClient(client)
    }
}


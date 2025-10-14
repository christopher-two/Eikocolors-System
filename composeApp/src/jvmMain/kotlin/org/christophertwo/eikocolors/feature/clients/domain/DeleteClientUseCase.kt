package org.christophertwo.eikocolors.feature.clients.domain

import org.christophertwo.eikocolors.feature.clients.desingsystem.ClientRepository

class DeleteClientUseCase(
    private val clientRepository: ClientRepository
) {
    suspend operator fun invoke(id: String) {
        clientRepository.deleteClient(id)
    }
}


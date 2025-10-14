package org.christophertwo.eikocolors.feature.clients.domain

import kotlinx.coroutines.flow.Flow
import org.christophertwo.eikocolors.feature.clients.desingsystem.ClientRepository
import org.christophertwo.eikocolors.feature.clients.domain.model.Client

class GetAllClientsUseCase(
    private val clientRepository: ClientRepository
) {
    operator fun invoke(): Flow<List<Client>> {
        return clientRepository.getAllClients()
    }
}


package org.christophertwo.eikocolors.feature.clients.domain

import kotlinx.coroutines.flow.Flow
import org.christophertwo.eikocolors.feature.clients.desingsystem.ClientRepository
import org.christophertwo.eikocolors.feature.clients.domain.model.Client

class SearchClientsUseCase(
    private val clientRepository: ClientRepository
) {
    operator fun invoke(query: String): Flow<List<Client>> {
        return clientRepository.searchClients(query)
    }
}

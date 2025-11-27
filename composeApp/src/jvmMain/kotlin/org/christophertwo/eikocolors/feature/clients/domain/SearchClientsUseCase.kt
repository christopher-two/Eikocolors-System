package org.christophertwo.eikocolors.feature.clients.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.christophertwo.eikocolors.domain.repository.AppRepository
import org.christophertwo.eikocolors.feature.clients.domain.model.Client

class SearchClientsUseCase(
    private val appRepository: AppRepository
) {
    operator fun invoke(query: String): Flow<List<Client>> {
        return appRepository.getClients().map { clients ->
            clients.filter { client ->
                client.name.contains(query, ignoreCase = true) ||
                client.email.contains(query, ignoreCase = true) ||
                client.phone.contains(query, ignoreCase = true) ||
                client.company?.contains(query, ignoreCase = true) == true
            }
        }
    }
}

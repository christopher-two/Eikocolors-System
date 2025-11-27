package org.christophertwo.eikocolors.feature.clients.domain

import kotlinx.coroutines.flow.Flow
import org.christophertwo.eikocolors.domain.repository.AppRepository
import org.christophertwo.eikocolors.feature.clients.domain.model.Client

class GetAllClientsUseCase(
    private val appRepository: AppRepository
) {
    operator fun invoke(): Flow<List<Client>> {
        return appRepository.getClients()
    }
}


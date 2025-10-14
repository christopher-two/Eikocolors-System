package org.christophertwo.eikocolors.feature.clients.desingsystem

import kotlinx.coroutines.flow.Flow
import org.christophertwo.eikocolors.feature.clients.domain.model.Client
import org.christophertwo.eikocolors.feature.clients.domain.model.ClientStatus

interface ClientRepository {
    fun getAllClients(): Flow<List<Client>>
    fun getClientById(id: String): Flow<Client?>
    fun getClientsByStatus(status: ClientStatus): Flow<List<Client>>
    suspend fun insertClient(client: Client)
    suspend fun updateClient(client: Client)
    suspend fun deleteClient(id: String)
    fun searchClients(query: String): Flow<List<Client>>
}


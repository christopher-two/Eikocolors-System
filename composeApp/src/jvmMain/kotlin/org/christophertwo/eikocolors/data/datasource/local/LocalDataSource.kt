package org.christophertwo.eikocolors.data.datasource.local

import kotlinx.coroutines.flow.Flow
import org.christophertwo.eikocolors.data.local.model.ClientEntity
import org.christophertwo.eikocolors.data.local.model.WorkEntity

interface LocalDataSource {
    fun getAllClients(): Flow<List<ClientEntity>>
    suspend fun insertAllClients(clients: List<ClientEntity>)
    suspend fun insertClient(client: ClientEntity)
    suspend fun updateClient(client: ClientEntity)
    suspend fun deleteClient(clientId: String)

    fun getAllWorks(): Flow<List<WorkEntity>>
    suspend fun insertAllWorks(works: List<WorkEntity>)
    suspend fun insertWork(work: WorkEntity)
    suspend fun updateWork(work: WorkEntity)
    suspend fun deleteWork(workId: String)
}


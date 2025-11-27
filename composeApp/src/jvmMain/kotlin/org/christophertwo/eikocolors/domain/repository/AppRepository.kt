package org.christophertwo.eikocolors.domain.repository

import kotlinx.coroutines.flow.Flow
import org.christophertwo.eikocolors.feature.clients.domain.model.Client
import org.christophertwo.eikocolors.feature.works.domain.model.Work

interface AppRepository {
    fun getClients(): Flow<List<Client>>
    suspend fun syncClients()
    suspend fun saveClient(client: Client)
    suspend fun updateClient(client: Client)
    suspend fun deleteClient(clientId: String)

    fun getWorks(): Flow<List<Work>>
    suspend fun syncWorks()
    suspend fun saveWork(work: Work)
    suspend fun updateWork(work: Work)
    suspend fun deleteWork(workId: String)
}


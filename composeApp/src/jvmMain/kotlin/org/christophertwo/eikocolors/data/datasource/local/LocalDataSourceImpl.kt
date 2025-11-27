package org.christophertwo.eikocolors.data.datasource.local

import kotlinx.coroutines.flow.Flow
import org.christophertwo.eikocolors.data.local.ClientDao
import org.christophertwo.eikocolors.data.local.WorkDao
import org.christophertwo.eikocolors.data.local.model.ClientEntity
import org.christophertwo.eikocolors.data.local.model.WorkEntity

class LocalDataSourceImpl(
    private val clientDao: ClientDao,
    private val workDao: WorkDao
) : LocalDataSource {
    override fun getAllClients(): Flow<List<ClientEntity>> = clientDao.getAllClients()
    override suspend fun insertAllClients(clients: List<ClientEntity>) = clientDao.insertAll(clients)
    override suspend fun insertClient(client: ClientEntity) = clientDao.insertClient(client)
    override suspend fun updateClient(client: ClientEntity) = clientDao.updateClient(client)
    override suspend fun deleteClient(clientId: String) = clientDao.deleteClient(clientId)

    override fun getAllWorks(): Flow<List<WorkEntity>> = workDao.getAllWorks()
    override suspend fun insertAllWorks(works: List<WorkEntity>) = workDao.insertAll(works)
    override suspend fun insertWork(work: WorkEntity) = workDao.insertWork(work)
    override suspend fun updateWork(work: WorkEntity) = workDao.updateWork(work)
    override suspend fun deleteWork(workId: String) = workDao.deleteWork(workId)
}


package org.christophertwo.eikocolors.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.christophertwo.eikocolors.data.datasource.local.LocalDataSource
import org.christophertwo.eikocolors.data.datasource.remote.RemoteDataSource
import org.christophertwo.eikocolors.data.local.model.toDomain
import org.christophertwo.eikocolors.data.local.model.toEntity
import org.christophertwo.eikocolors.domain.repository.AppRepository
import org.christophertwo.eikocolors.feature.clients.domain.model.Client
import org.christophertwo.eikocolors.feature.works.domain.model.Work

class AppRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : AppRepository {

    override fun getClients(): Flow<List<Client>> {
        return localDataSource.getAllClients().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun syncClients() {
        val remoteClients = remoteDataSource.getClients().first()
        localDataSource.insertAllClients(remoteClients.map { it.toEntity() })
    }

    override suspend fun saveClient(client: Client) {
        remoteDataSource.saveClient(client)
        localDataSource.insertClient(client.toEntity())
    }

    override suspend fun updateClient(client: Client) {
        remoteDataSource.updateClient(client)
        localDataSource.updateClient(client.toEntity())
    }

    override suspend fun deleteClient(clientId: String) {
        remoteDataSource.deleteClient(clientId)
        localDataSource.deleteClient(clientId)
    }

    override fun getWorks(): Flow<List<Work>> {
        return localDataSource.getAllWorks().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun syncWorks() {
        val remoteWorks = remoteDataSource.getWorks().first()
        localDataSource.insertAllWorks(remoteWorks.map { it.toEntity() })
    }

    override suspend fun saveWork(work: Work) {
        remoteDataSource.saveWork(work)
        localDataSource.insertWork(work.toEntity())
    }

    override suspend fun updateWork(work: Work) {
        remoteDataSource.updateWork(work)
        localDataSource.updateWork(work.toEntity())
    }

    override suspend fun deleteWork(workId: String) {
        remoteDataSource.deleteWork(workId)
        localDataSource.deleteWork(workId)
    }
}


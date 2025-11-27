package org.christophertwo.eikocolors.data.datasource.remote

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.christophertwo.eikocolors.feature.clients.domain.model.Client
import org.christophertwo.eikocolors.feature.works.domain.model.Work

class RemoteDataSourceImpl : RemoteDataSource {

    private val db = Firebase.firestore

    override fun getClients(): Flow<List<Client>> {
        return db.collection("clients").snapshots.map { snapshot ->
            snapshot.documents.map { it.data() }
        }
    }

    override suspend fun saveClient(client: Client) {
        db.collection("clients").document(client.id).set(client)
    }

    override suspend fun updateClient(client: Client) {
        db.collection("clients").document(client.id).set(client, merge = true)
    }

    override suspend fun deleteClient(clientId: String) {
        db.collection("clients").document(clientId).delete()
    }

    override fun getWorks(): Flow<List<Work>> {
        return db.collection("works").snapshots.map { snapshot ->
            snapshot.documents.map { it.data() }
        }
    }

    override suspend fun saveWork(work: Work) {
        db.collection("works").document(work.id).set(work)
    }

    override suspend fun updateWork(work: Work) {
        db.collection("works").document(work.id).set(work, merge = true)
    }

    override suspend fun deleteWork(workId: String) {
        db.collection("works").document(workId).delete()
    }
}


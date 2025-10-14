package org.christophertwo.eikocolors.feature.works.desingsystem

import kotlinx.coroutines.flow.Flow
import org.christophertwo.eikocolors.feature.works.domain.model.Work
import org.christophertwo.eikocolors.feature.works.domain.model.WorkStatus

interface WorkRepository {
    fun getAllWorks(): Flow<List<Work>>
    fun getWorkById(id: String): Flow<Work?>
    fun getWorksByStatus(status: WorkStatus): Flow<List<Work>>
    suspend fun insertWork(work: Work)
    suspend fun updateWork(work: Work)
    suspend fun deleteWork(id: String)
    fun searchWorks(query: String): Flow<List<Work>>
}
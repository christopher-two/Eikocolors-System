package org.christophertwo.eikocolors.feature.works.domain

import kotlinx.coroutines.flow.Flow
import org.christophertwo.eikocolors.feature.works.desingsystem.WorkRepository
import org.christophertwo.eikocolors.feature.works.domain.model.Work

class GetAllWorksUseCase(
    private val workRepository: WorkRepository
) {
    operator fun invoke(): Flow<List<Work>> {
        return workRepository.getAllWorks()
    }
}


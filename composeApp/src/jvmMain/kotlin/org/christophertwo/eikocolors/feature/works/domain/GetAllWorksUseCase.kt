package org.christophertwo.eikocolors.feature.works.domain

import kotlinx.coroutines.flow.Flow
import org.christophertwo.eikocolors.domain.repository.AppRepository
import org.christophertwo.eikocolors.feature.works.domain.model.Work

class GetAllWorksUseCase(
    private val appRepository: AppRepository
) {
    operator fun invoke(): Flow<List<Work>> {
        return appRepository.getWorks()
    }
}


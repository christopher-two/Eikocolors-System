package org.christophertwo.eikocolors.feature.works.domain

import org.christophertwo.eikocolors.domain.repository.AppRepository
import org.christophertwo.eikocolors.feature.works.domain.model.Work

class UpdateWorkUseCase(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(work: Work) {
        appRepository.updateWork(work)
    }
}


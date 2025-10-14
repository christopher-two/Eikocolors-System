package org.christophertwo.eikocolors.feature.works.domain

import org.christophertwo.eikocolors.feature.works.desingsystem.WorkRepository
import org.christophertwo.eikocolors.feature.works.domain.model.Work

class UpdateWorkUseCase(
    private val workRepository: WorkRepository
) {
    suspend operator fun invoke(work: Work) {
        workRepository.updateWork(work)
    }
}


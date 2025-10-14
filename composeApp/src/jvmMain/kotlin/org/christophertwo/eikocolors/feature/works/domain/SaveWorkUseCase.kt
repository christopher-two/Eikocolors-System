package org.christophertwo.eikocolors.feature.works.domain

import org.christophertwo.eikocolors.feature.works.desingsystem.WorkRepository
import org.christophertwo.eikocolors.feature.works.domain.model.Work

class SaveWorkUseCase(
    private val workRepository: WorkRepository
) {
    suspend operator fun invoke(work: Work) {
        workRepository.insertWork(work)
    }
}


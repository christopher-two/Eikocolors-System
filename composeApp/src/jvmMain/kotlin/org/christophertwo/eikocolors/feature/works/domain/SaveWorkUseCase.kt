package org.christophertwo.eikocolors.feature.works.domain

import org.christophertwo.eikocolors.domain.repository.AppRepository
import org.christophertwo.eikocolors.feature.works.domain.model.Work

class SaveWorkUseCase(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(work: Work) {
        appRepository.saveWork(work)
    }
}


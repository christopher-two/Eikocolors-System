package org.christophertwo.eikocolors.feature.works.domain

import org.christophertwo.eikocolors.domain.repository.AppRepository

class DeleteWorkUseCase(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(id: String) {
        appRepository.deleteWork(id)
    }
}

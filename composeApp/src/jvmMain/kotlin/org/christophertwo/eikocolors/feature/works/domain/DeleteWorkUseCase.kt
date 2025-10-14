package org.christophertwo.eikocolors.feature.works.domain

import org.christophertwo.eikocolors.feature.works.desingsystem.WorkRepository

class DeleteWorkUseCase(
    private val workRepository: WorkRepository
) {
    suspend operator fun invoke(id: String) {
        workRepository.deleteWork(id)
    }
}

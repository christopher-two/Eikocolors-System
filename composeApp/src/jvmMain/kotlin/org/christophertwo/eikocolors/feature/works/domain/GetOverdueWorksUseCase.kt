package org.christophertwo.eikocolors.feature.works.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.christophertwo.eikocolors.feature.works.desingsystem.WorkRepository
import org.christophertwo.eikocolors.feature.works.domain.model.Work
import org.christophertwo.eikocolors.feature.works.domain.model.WorkStatus
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class GetOverdueWorksUseCase(
    private val workRepository: WorkRepository
) {
    @OptIn(ExperimentalTime::class)
    operator fun invoke(): Flow<List<Work>> {
        return workRepository.getAllWorks().map { works ->
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            works.filter { work ->
                work.status != WorkStatus.COMPLETED &&
                        work.status != WorkStatus.CANCELLED &&
                        work.dueDate < now
            }
        }
    }
}

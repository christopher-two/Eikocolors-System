package org.christophertwo.eikocolors.feature.works.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class WorkStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}


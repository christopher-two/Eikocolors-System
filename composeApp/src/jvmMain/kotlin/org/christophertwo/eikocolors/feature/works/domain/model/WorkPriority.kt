package org.christophertwo.eikocolors.feature.works.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class WorkPriority {
    LOW,
    MEDIUM,
    HIGH,
    URGENT
}


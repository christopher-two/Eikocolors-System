package org.christophertwo.eikocolors.feature.clients.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class ClientStatus {
    ACTIVE,
    INACTIVE,
    POTENTIAL
}


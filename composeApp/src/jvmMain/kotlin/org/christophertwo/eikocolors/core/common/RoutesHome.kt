package org.christophertwo.eikocolors.core.common

import kotlinx.serialization.Serializable

@Serializable
sealed class RoutesHome(
    val displayName: String,
) {
    @Serializable
    object Clients : RoutesHome("Clients")

    @Serializable
    object Works : RoutesHome("Trabajos")
}
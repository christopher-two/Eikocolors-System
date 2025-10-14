package org.christophertwo.eikocolors.core.common

import kotlinx.serialization.Serializable

@Serializable
sealed class RoutesStart(
    val displayName: String,
) {
    @Serializable
    object Home : RoutesStart("Home")
}
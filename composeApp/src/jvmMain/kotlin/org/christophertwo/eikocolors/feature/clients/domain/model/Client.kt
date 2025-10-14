package org.christophertwo.eikocolors.feature.clients.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Client(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val address: String? = null,
    val company: String? = null,
    val status: ClientStatus,
    val totalWorks: Int = 0,
    val activeWorks: Int = 0,
    val completedWorks: Int = 0,
    val notes: String? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val lastContactDate: LocalDateTime? = null
)

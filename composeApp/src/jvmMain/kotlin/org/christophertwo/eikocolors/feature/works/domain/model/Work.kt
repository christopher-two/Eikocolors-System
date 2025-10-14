package org.christophertwo.eikocolors.feature.works.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Work(
    val id: String,
    val title: String,
    val description: String,
    val clientId: String,
    val clientName: String,
    val status: WorkStatus,
    val priority: WorkPriority,
    val dueDate: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val estimatedHours: Double? = null,
    val actualHours: Double? = null,
    val notes: String? = null
)
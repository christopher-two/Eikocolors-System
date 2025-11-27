package org.christophertwo.eikocolors.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime
import org.christophertwo.eikocolors.feature.works.domain.model.Work
import org.christophertwo.eikocolors.feature.works.domain.model.WorkPriority
import org.christophertwo.eikocolors.feature.works.domain.model.WorkStatus

@Entity(tableName = "works")
data class WorkEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val clientId: String,
    val clientName: String,
    val status: WorkStatus,
    val priority: WorkPriority,
    val dueDate: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val estimatedHours: Double?,
    val actualHours: Double?,
    val notes: String?
)

fun WorkEntity.toDomain() = Work(
    id = id,
    title = title,
    description = description,
    clientId = clientId,
    clientName = clientName,
    status = status,
    priority = priority,
    dueDate = dueDate,
    createdAt = createdAt,
    updatedAt = updatedAt,
    estimatedHours = estimatedHours,
    actualHours = actualHours,
    notes = notes
)

fun Work.toEntity() = WorkEntity(
    id = id,
    title = title,
    description = description,
    clientId = clientId,
    clientName = clientName,
    status = status,
    priority = priority,
    dueDate = dueDate,
    createdAt = createdAt,
    updatedAt = updatedAt,
    estimatedHours = estimatedHours,
    actualHours = actualHours,
    notes = notes
)


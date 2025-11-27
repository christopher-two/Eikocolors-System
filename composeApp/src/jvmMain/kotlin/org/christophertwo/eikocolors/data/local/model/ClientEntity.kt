package org.christophertwo.eikocolors.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime
import org.christophertwo.eikocolors.feature.clients.domain.model.Client
import org.christophertwo.eikocolors.feature.clients.domain.model.ClientStatus

@Entity(tableName = "clients")
data class ClientEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val address: String?,
    val company: String?,
    val status: ClientStatus,
    val totalWorks: Int,
    val activeWorks: Int,
    val completedWorks: Int,
    val notes: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val lastContactDate: LocalDateTime?
)

fun ClientEntity.toDomain() = Client(
    id = id,
    name = name,
    email = email,
    phone = phone,
    address = address,
    company = company,
    status = status,
    totalWorks = totalWorks,
    activeWorks = activeWorks,
    completedWorks = completedWorks,
    notes = notes,
    createdAt = createdAt,
    updatedAt = updatedAt,
    lastContactDate = lastContactDate
)

fun Client.toEntity() = ClientEntity(
    id = id,
    name = name,
    email = email,
    phone = phone,
    address = address,
    company = company,
    status = status,
    totalWorks = totalWorks,
    activeWorks = activeWorks,
    completedWorks = completedWorks,
    notes = notes,
    createdAt = createdAt,
    updatedAt = updatedAt,
    lastContactDate = lastContactDate
)


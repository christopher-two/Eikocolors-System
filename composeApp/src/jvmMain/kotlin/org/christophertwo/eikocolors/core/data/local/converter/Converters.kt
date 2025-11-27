package org.christophertwo.eikocolors.core.data.local.converter

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.christophertwo.eikocolors.feature.clients.domain.model.ClientStatus
import org.christophertwo.eikocolors.feature.works.domain.model.WorkPriority
import org.christophertwo.eikocolors.feature.works.domain.model.WorkStatus
import kotlin.time.ExperimentalTime

class Converters {
    @OptIn(ExperimentalTime::class)
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.UTC) }
    }

    @OptIn(ExperimentalTime::class)
    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.toInstant(TimeZone.UTC)?.toEpochMilliseconds()
    }

    @TypeConverter
    fun fromClientStatus(status: ClientStatus): String = status.name

    @TypeConverter
    fun toClientStatus(status: String): ClientStatus = ClientStatus.valueOf(status)

    @TypeConverter
    fun fromWorkStatus(status: WorkStatus): String = status.name

    @TypeConverter
    fun toWorkStatus(status: String): WorkStatus = WorkStatus.valueOf(status)

    @TypeConverter
    fun fromWorkPriority(priority: WorkPriority): String = priority.name

    @TypeConverter
    fun toWorkPriority(priority: String): WorkPriority = WorkPriority.valueOf(priority)
}


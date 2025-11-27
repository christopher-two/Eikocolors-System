package org.christophertwo.eikocolors.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.christophertwo.eikocolors.core.data.local.converter.Converters
import org.christophertwo.eikocolors.data.local.model.ClientEntity
import org.christophertwo.eikocolors.data.local.model.WorkEntity

@Database(entities = [ClientEntity::class, WorkEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
    abstract fun workDao(): WorkDao
}


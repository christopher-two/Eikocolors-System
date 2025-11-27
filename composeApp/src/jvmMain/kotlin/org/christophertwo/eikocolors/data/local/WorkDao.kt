package org.christophertwo.eikocolors.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.christophertwo.eikocolors.data.local.model.WorkEntity

@Dao
interface WorkDao {

    @Query("SELECT * FROM works")
    fun getAllWorks(): Flow<List<WorkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(works: List<WorkEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWork(work: WorkEntity)

    @Update
    suspend fun updateWork(work: WorkEntity)

    @Query("DELETE FROM works WHERE id = :workId")
    suspend fun deleteWork(workId: String)
}


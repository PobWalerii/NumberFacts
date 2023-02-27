package com.example.numberfacts.data.database.dao

import androidx.room.*
import com.example.numberfacts.data.database.entitys.Numbers
import kotlinx.coroutines.flow.Flow

@Dao
interface NumbersDao {
    @Query("SELECT * FROM Numbers ORDER BY time DESC")
    fun getNumbersList(): Flow<List<Numbers>>

    @Query("SELECT * FROM Numbers WHERE number = :search ORDER BY time DESC")
    fun getNumberFacts(search: Int): Flow<List<Numbers>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumberFact(item: Numbers)

    @Delete
    suspend fun deleteFact(item: Numbers)

}
package com.example.numberfacts.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.numberfacts.data.database.entitys.Numbers
import kotlinx.coroutines.flow.Flow

@Dao
interface NumbersDao {
    @Query("SELECT * FROM Numbers ORDER BY time")
    fun getNumbersList(): Flow<List<Numbers>>

    @Query("SELECT * FROM Numbers WHERE number = :search ORDER BY time")
    fun getNumberFacts(search: Int): Flow<List<Numbers>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumberFact(item: Numbers): Long


}
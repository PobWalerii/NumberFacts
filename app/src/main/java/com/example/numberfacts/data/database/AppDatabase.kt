package com.example.numberfacts.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.numberfacts.data.database.dao.NumbersDao
import com.example.numberfacts.data.database.entitys.Numbers

@Database(entities = [Numbers::class], version = 4, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun numbersDao(): NumbersDao

}
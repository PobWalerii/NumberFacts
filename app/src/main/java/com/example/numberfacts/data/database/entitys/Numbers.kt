package com.example.numberfacts.data.database.entitys

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["number","fact"], unique = true)])
data class Numbers (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val number: Int,
    val time: Long,
    val fact: String
)

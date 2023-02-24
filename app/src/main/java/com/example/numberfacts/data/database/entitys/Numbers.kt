package com.example.numberfacts.data.database.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Numbers (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val number: Int
)

package com.example.numberfacts.data.database.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Facts(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val idNumber: Long,
    val time: Long,
    val txtFact: String
)
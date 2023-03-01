package com.example.numberfacts.data.api

data class NumberResponse (
    val text: String,
    val number: Int,
    val found: Boolean,
    val type: String
)
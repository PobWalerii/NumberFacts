package com.example.numberfacts.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET()
    suspend fun getYoutube(
        @Query("number") number: String
    ): String
}
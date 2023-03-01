package com.example.numberfacts.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("{number}")
    suspend fun getFactForNumber(
        @Path("number") number: String,
        @Query("type") type: String,
        @Query("json") json: Boolean
    ): NumberResponse

    @GET("random")
    suspend fun getFactForRandom(
        @Query("type") type: String,
        @Query("json") json: Boolean
    ): NumberResponse
}
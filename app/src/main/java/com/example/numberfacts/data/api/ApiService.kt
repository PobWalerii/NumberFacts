package com.example.numberfacts.data.api


import com.example.numberfacts.constants.KeyConstants.GET_RANDOM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("{number}")
    suspend fun getFactForNumber(
        @Path("number") number: Int
    ): Response<String>

    @GET(GET_RANDOM)
    suspend fun getFactForRandom(
        @Path("type") type: String
    ): Response<String>
}
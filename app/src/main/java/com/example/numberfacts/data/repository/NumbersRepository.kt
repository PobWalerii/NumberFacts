package com.example.numberfacts.data.repository

import com.example.numberfacts.data.api.ApiService
import com.example.numberfacts.data.database.dao.NumbersDao
import com.example.numberfacts.data.database.entitys.Numbers
import com.example.numberfacts.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NumbersRepository @Inject constructor(
    private val apiService: ApiService,
    private val numbersDao: NumbersDao
){

    fun getNumbersFacts(key: Int): Flow<ResponseState<List<Numbers>>> {
        return flow {








        }
    }






}
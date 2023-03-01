package com.example.numberfacts.data.repository

import com.example.numberfacts.data.api.ApiService
import com.example.numberfacts.data.api.NumberResponse
import com.example.numberfacts.data.database.dao.NumbersDao
import com.example.numberfacts.data.database.entitys.Numbers
import com.example.numberfacts.ui.queryfragment.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class NumbersRepository @Inject constructor(
    private val apiService: ApiService,
    private val numbersDao: NumbersDao
){
    fun getNumbersFacts(key: Int?): Flow<ResponseState<String>> {
        return flow {
            emit(ResponseState.Loading(true))
            try {
                val response: NumberResponse =
                    if (key == null) {
                        apiService.getFactForRandom("math",true)
                    } else {
                        apiService.getFactForNumber("$key","math",true)
                    }
                if (response.found) {
                    saveToDatabase(response)
                    emit(ResponseState.Success(response.text))
                } else {
                    emit(ResponseState.Success(null))
                }
            } catch (exception: Exception) {
                emit(ResponseState.Error(exception.message))
            }
            emit(ResponseState.Loading(false))
        }

    }

    private suspend fun saveToDatabase(response: NumberResponse) {

        val index = response.text.indexOfFirst { !it.isDigit() }
        val fact = response.text.substring(index).trimStart()
        val number: Int = response.number

        numbersDao.insertNumberFact(Numbers(
            0,
            number,
            Date().time,
            fact
        ))
    }

    fun getNumbersList(): Flow<List<Numbers>> = numbersDao.getNumbersList()

    fun getNumberFacts(search: Int): Flow<List<Numbers>> = numbersDao.getNumberFacts(search)

    suspend fun deleteFact(item: Numbers) {
        numbersDao.deleteFact(item)
    }
}

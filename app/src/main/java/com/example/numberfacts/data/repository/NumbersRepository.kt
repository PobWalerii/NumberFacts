package com.example.numberfacts.data.repository

import com.example.numberfacts.constants.KeyConstants.RANDOM_TYPE
import com.example.numberfacts.data.api.ApiService
import com.example.numberfacts.data.database.dao.NumbersDao
import com.example.numberfacts.data.database.entitys.Numbers
import com.example.numberfacts.utils.ResponseState
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
                val response: String? =
                    if (key==null) {
                        apiService.getFactForRandom(RANDOM_TYPE).body()
                    } else {
                        apiService.getFactForNumber(
                            key
                        ).body()
                    }
                if (response != null) {
                    saveToDatabase(response)
                }
                emit(ResponseState.Success(response))
            } catch (exception: Exception) {
                emit(ResponseState.Error(exception.message))
            }
            emit(ResponseState.Loading(false))
        }
    }

    private suspend fun saveToDatabase(response: String) {
        val index = response.indexOfFirst { !it.isDigit() }
        val number = response.substring(0, index).toInt()
        val fact = response.substring(index)
        val item = Numbers(
            0,
            number,
            Date().time,
            fact
        )
        numbersDao.insertNumberFact(item)
    }

    fun getNumbersList(): Flow<List<Numbers>> = numbersDao.getNumbersList()


}

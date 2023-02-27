package com.example.numberfacts.data.repository

import android.net.Uri
import com.example.numberfacts.data.api.ApiService
import com.example.numberfacts.data.database.dao.NumbersDao
import com.example.numberfacts.data.database.entitys.Numbers
import com.example.numberfacts.ui.queryfragment.ResponseState
import com.example.numberfacts.utils.KeyConstants.RANDOM_TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.URLEncoder
import java.util.*
import javax.inject.Inject
import kotlin.math.abs

class NumbersRepository @Inject constructor(
    private val apiService: ApiService,
    private val numbersDao: NumbersDao
){
    fun getNumbersFacts(key: Int?): Flow<ResponseState<String>> {
        return flow {
            emit(ResponseState.Loading(true))
            try {
                val response: String? =
                    if (key == null) {
                        apiService.getFactForRandom(RANDOM_TYPE).body()
                    } else if (key < 0) {
                        val encodedNumber = key.toString()
                        apiService.getFactForNegative("$encodedNumber?json", true).body()
                    } else {
                        apiService.getFactForNumber(key.toString()).body()
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
        val fact = response.substring(index).trimStart()
        val item = Numbers(
            0,
            number,
            Date().time,
            fact
        )
        numbersDao.insertNumberFact(item)
    }

    fun getNumbersList(): Flow<List<Numbers>> = numbersDao.getNumbersList()

    fun getNumberFacts(search: Int): Flow<List<Numbers>> = numbersDao.getNumberFacts(search)

    suspend fun deleteFact(item: Numbers) {
        numbersDao.deleteFact(item)
    }
}

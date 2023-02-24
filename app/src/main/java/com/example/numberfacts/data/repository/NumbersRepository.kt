package com.example.numberfacts.data.repository

import com.example.numberfacts.data.api.ApiService
import com.example.numberfacts.data.database.dao.NumbersDao
import javax.inject.Inject

class NumbersRepository @Inject constructor(
    private val apiService: ApiService,
    private val numbersDao: NumbersDao
){

    fun getNumbersFacts(key: Int) {




    }






}
package com.example.numberfacts.di

import android.content.Context
import androidx.room.Room
import com.example.numberfacts.constants.KeyConstants
import com.example.numberfacts.data.api.ApiService
import com.example.numberfacts.data.database.AppDatabase
import com.example.numberfacts.data.database.dao.NumbersDao
import com.example.numberfacts.data.repository.NumbersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            KeyConstants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideNumbersDao(database: AppDatabase): NumbersDao {
        return database.numbersDao()
    }
    @Provides
    fun provideNumbersRepository(
        apiService: ApiService,
        numbersDao: NumbersDao
    ): NumbersRepository {
        return NumbersRepository(apiService, numbersDao)
    }

}

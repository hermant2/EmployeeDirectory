package com.treyherman.employeedirectory

import android.content.Context
import androidx.room.Room
import com.treyherman.employeedirectory.persistence.EmployeeDirectoryDatabase
import com.treyherman.employeedirectory.persistence.dao.EmployeeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {
    companion object {
        private const val DATABASE_NAME = "employee_database"
    }

    @Provides
    @Singleton
    fun provideEmployeeDirectoryDatabase(context: Context): EmployeeDirectoryDatabase {
        return Room.databaseBuilder(context, EmployeeDirectoryDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideEmployeeDao(database: EmployeeDirectoryDatabase): EmployeeDao {
        return database.employeeDao()
    }
}

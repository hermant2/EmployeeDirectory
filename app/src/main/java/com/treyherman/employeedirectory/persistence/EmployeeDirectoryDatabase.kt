package com.treyherman.employeedirectory.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.treyherman.employeedirectory.persistence.dao.EmployeeDao
import com.treyherman.employeedirectory.persistence.model.EmployeeEntity

@Database(entities = [EmployeeEntity::class], version = 1)
abstract class EmployeeDirectoryDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}
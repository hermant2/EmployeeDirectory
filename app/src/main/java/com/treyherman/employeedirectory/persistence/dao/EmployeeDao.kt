package com.treyherman.employeedirectory.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.treyherman.employeedirectory.persistence.model.EmployeeEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployeesCompletable(employees: List<EmployeeEntity>): Completable

    @Query("SELECT * FROM EmployeeEntity")
    fun localEmployeesStream(): Observable<List<EmployeeEntity>>
}
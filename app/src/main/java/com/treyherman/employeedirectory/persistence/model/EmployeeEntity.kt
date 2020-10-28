package com.treyherman.employeedirectory.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EmployeeEntity(
    @PrimaryKey val uuid: String,
    val fullName: String,
    val phoneNumber: String?,
    val emailAddress: String,
    val biography: String?,
    val photoUrlSmall: String?,
    val photoUrlLarge: String?,
    val team: String,
    val employeeType: String
)

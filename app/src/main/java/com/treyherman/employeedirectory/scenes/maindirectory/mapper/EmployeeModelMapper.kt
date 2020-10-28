package com.treyherman.employeedirectory.scenes.maindirectory.mapper

import com.treyherman.employeedirectory.persistence.model.EmployeeEntity
import com.treyherman.employeedirectory.rest.model.response.employee.EmployeeResponseWrapper
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee

interface EmployeeModelMapper {
    fun mapEmployeeEntities(responseWrapper: EmployeeResponseWrapper): List<EmployeeEntity>

    fun mapUIEmployees(employeeEntities: List<EmployeeEntity>): List<UIEmployee>
}

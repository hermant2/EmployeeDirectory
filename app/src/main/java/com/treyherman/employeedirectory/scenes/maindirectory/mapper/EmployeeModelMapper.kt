package com.treyherman.employeedirectory.scenes.maindirectory.mapper

import com.treyherman.employeedirectory.rest.model.response.employee.EmployeeResponseWrapper
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee

interface EmployeeModelMapper {
    fun mapEmployees(responseWrapper: EmployeeResponseWrapper): List<UIEmployee>
}

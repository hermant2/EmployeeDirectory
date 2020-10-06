package com.treyherman.employeedirectory.rest.service

import com.treyherman.employeedirectory.rest.model.response.employee.EmployeeResponseWrapper
import io.reactivex.Single

interface EmployeeApiService {
    fun employeesOnce(): Single<EmployeeResponseWrapper>

    fun malformedEmployeesOnce(): Single<EmployeeResponseWrapper>

    fun emptyEmployeesOnce(): Single<EmployeeResponseWrapper>
}

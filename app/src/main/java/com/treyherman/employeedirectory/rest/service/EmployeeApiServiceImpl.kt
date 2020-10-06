package com.treyherman.employeedirectory.rest.service

import com.treyherman.employeedirectory.extension.subscribeOnIO
import com.treyherman.employeedirectory.rest.ApiService
import com.treyherman.employeedirectory.rest.model.response.employee.EmployeeResponseWrapper
import io.reactivex.Single
import javax.inject.Inject

class EmployeeApiServiceImpl @Inject constructor(
    private val apiService: ApiService
) : EmployeeApiService {

    override fun employeesOnce(): Single<EmployeeResponseWrapper> {
        return apiService.employeesOnce().subscribeOnIO()
    }

    override fun malformedEmployeesOnce(): Single<EmployeeResponseWrapper> {
        return apiService.malformedEmployeesOnce().subscribeOnIO()
    }

    override fun emptyEmployeesOnce(): Single<EmployeeResponseWrapper> {
        return apiService.emptyEmployeesOnce().subscribeOnIO()
    }
}

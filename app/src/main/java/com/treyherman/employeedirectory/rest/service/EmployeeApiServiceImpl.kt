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
}

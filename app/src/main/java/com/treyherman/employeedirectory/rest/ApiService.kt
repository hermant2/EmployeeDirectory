package com.treyherman.employeedirectory.rest

import com.treyherman.employeedirectory.rest.model.response.employee.EmployeeResponseWrapper
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
    @GET("employees.json")
    fun employeesOnce(): Single<EmployeeResponseWrapper>
}

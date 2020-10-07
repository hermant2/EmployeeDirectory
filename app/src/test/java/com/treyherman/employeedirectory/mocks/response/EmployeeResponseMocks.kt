package com.treyherman.employeedirectory.mocks.response

import com.treyherman.employeedirectory.rest.model.response.employee.EmployeeResponse
import java.util.*

fun mockEmployeeResponse(
    uuid: String = UUID.randomUUID().toString(),
    fullName: String = "Full Name",
    phoneNumber: String? = "3334445566",
    emailAddress: String = "test@user.com",
    biography: String? = "bio",
    photoUrlSmall: String? = "url_small.png",
    photoUrlLarge: String? = "url_large.png",
    team: String = "Engineering",
    employeeType: String = "FULL_TIME"
): EmployeeResponse {
    return EmployeeResponse(
        uuid,
        fullName,
        phoneNumber,
        emailAddress,
        biography,
        photoUrlSmall,
        photoUrlLarge,
        team,
        employeeType
    )
}

package com.treyherman.employeedirectory.scenes.maindirectory.mapper

import android.content.res.Resources
import com.treyherman.employeedirectory.R
import com.treyherman.employeedirectory.rest.model.response.employee.EmployeeResponse
import com.treyherman.employeedirectory.rest.model.response.employee.EmployeeResponseWrapper
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee
import javax.inject.Inject

class EmployeeModelMapperImpl @Inject constructor(
    private val resources: Resources
) : EmployeeModelMapper {
    override fun mapEmployees(responseWrapper: EmployeeResponseWrapper): List<UIEmployee> {
        return responseWrapper.employees.map { mapEmployee(it) }
    }

    // region private
    private fun mapEmployee(response: EmployeeResponse): UIEmployee {
        val nameAndTeam =
            resources.getString(R.string.formatted_name_and_team, response.fullName, response.team)
        return UIEmployee(
            response.uuid,
            nameAndTeam,
            response.phoneNumber,
            response.emailAddress,
            response.biography,
            response.photoUrlSmall,
            mapEmployeeClassificationText(response.type)
        )
    }

    private fun mapEmployeeClassificationText(employeeType: EmployeeResponse.Type): String {
        return when (employeeType) {
            EmployeeResponse.Type.FULL_TIME -> resources.getString(R.string.full_time_employee)
            EmployeeResponse.Type.PART_TIME -> resources.getString(R.string.part_time_employee)
            EmployeeResponse.Type.CONTRACTOR -> resources.getString(R.string.contractor_employee)
        }
    }
    // endregion private
}

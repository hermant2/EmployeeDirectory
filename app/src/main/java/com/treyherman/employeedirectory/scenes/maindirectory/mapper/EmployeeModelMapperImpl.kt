package com.treyherman.employeedirectory.scenes.maindirectory.mapper

import android.content.res.Resources
import com.treyherman.employeedirectory.R
import com.treyherman.employeedirectory.manager.phonenumber.PhoneNumberFormatManager
import com.treyherman.employeedirectory.persistence.model.EmployeeEntity
import com.treyherman.employeedirectory.rest.model.response.employee.EmployeeResponse
import com.treyherman.employeedirectory.rest.model.response.employee.EmployeeResponseWrapper
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee
import javax.inject.Inject

class EmployeeModelMapperImpl @Inject constructor(
    private val resources: Resources,
    private val phoneNumberFormatManager: PhoneNumberFormatManager
) : EmployeeModelMapper {
    override fun mapEmployeeEntities(responseWrapper: EmployeeResponseWrapper): List<EmployeeEntity> {
        return responseWrapper.employees.map {
            EmployeeEntity(
                it.uuid,
                it.fullName,
                it.phoneNumber,
                it.emailAddress,
                it.biography,
                it.photoUrlSmall,
                it.photoUrlLarge,
                it.team,
                it.employeeType
            )
        }
    }

    override fun mapUIEmployees(employeeEntities: List<EmployeeEntity>): List<UIEmployee> {
        return employeeEntities.map { mapEmployee(it) }
    }

    // region private
    private fun mapEmployee(entity: EmployeeEntity): UIEmployee {
        val nameAndTeam =
            resources.getString(R.string.formatted_name_and_team, entity.fullName, entity.team)
        return UIEmployee(
            entity.uuid,
            nameAndTeam,
            phoneNumberFormatManager.formatPhoneNumber(entity.phoneNumber),
            entity.emailAddress,
            entity.biography,
            entity.photoUrlSmall,
            mapEmployeeClassificationText(EmployeeResponse.Type.valueOf(entity.employeeType))
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

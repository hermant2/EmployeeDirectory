package com.treyherman.employeedirectory.scenes.maindirectory.mapper

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.treyherman.employeedirectory.R
import com.treyherman.employeedirectory.manager.phonenumber.PhoneNumberFormatManager
import com.treyherman.employeedirectory.mocks.response.mockEmployeeResponse
import com.treyherman.employeedirectory.rest.model.response.employee.EmployeeResponse
import com.treyherman.employeedirectory.rest.model.response.employee.EmployeeResponseWrapper
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee
import org.junit.Assert.*
import org.junit.Test

class EmployeeModelMapperTest {

    companion object {
        private const val FULL_TIME = "Full time"
        private const val PART_TIME = "Part time"
        private const val CONTRACT = "Contract"
        private const val FORMATTED_NAME_AND_TEAM = "formatted name and team"
        private const val MOCK_PHONE_NUMBER = "3334445566"
        private const val MOCK_FORMATTED_NUMBER = "(333) 444-5566"
    }

    private val resources = mock<Resources> {
        on { getString(R.string.full_time_employee) }.thenReturn(FULL_TIME)
        on { getString(R.string.part_time_employee) }.thenReturn(PART_TIME)
        on { getString(R.string.contractor_employee) }.thenReturn(CONTRACT)
        on { getString(eq(R.string.formatted_name_and_team), any(), any()) }.thenReturn(
            FORMATTED_NAME_AND_TEAM
        )
    }

    private val phoneNumberFormatManager = mock<PhoneNumberFormatManager> {
        on { formatPhoneNumber(MOCK_PHONE_NUMBER) }.thenReturn(MOCK_FORMATTED_NUMBER)
        on { formatPhoneNumber(null) }.thenReturn(null)
    }

    private val employeeModelMapper: EmployeeModelMapper by lazy {
        EmployeeModelMapperImpl(resources, phoneNumberFormatManager)
    }

    private val employeesResponseWrapper by lazy {
        val employeeResponse1 = mockEmployeeResponse(
            fullName = "Guy Fieri",
            phoneNumber = MOCK_PHONE_NUMBER,
            team = "Engineering",
            employeeType = "FULL_TIME"
        )
        val employeeResponse2 = mockEmployeeResponse(
            fullName = "Other Person",
            phoneNumber = null,
            team = "Sales",
            employeeType = "PART_TIME"
        )
        val employeeResponse3 = mockEmployeeResponse(
            fullName = "Last One",
            phoneNumber = null,
            team = "Accounting",
            employeeType = "CONTRACTOR"
        )
        val employeeResponse4 = mockEmployeeResponse(
            fullName = "Ghost",
            phoneNumber = null,
            team = "Product",
            employeeType = "UNKNOWN"
        )

        EmployeeResponseWrapper(
            listOf(
                employeeResponse1,
                employeeResponse2,
                employeeResponse3,
                employeeResponse4
            )
        )
    }

    @Test
    fun mapEmployees_result() {
        val employees = employeeModelMapper.mapUIEmployees(employeesResponseWrapper)

        assertEquals(employees.size, employeesResponseWrapper.employees.size)
        employees.forEachIndexed { index, employee ->
            assertEmployee(employee, employeesResponseWrapper.employees[index])
        }
    }

    private fun assertEmployee(uiEmployee: UIEmployee, response: EmployeeResponse) {
        assertEquals(uiEmployee.uuid, response.uuid)
        assertEquals(uiEmployee.nameAndTeam, FORMATTED_NAME_AND_TEAM)
        assertEquals(uiEmployee.bio, response.biography)
        assertEquals(uiEmployee.photoUrl, response.photoUrlSmall)
        response.phoneNumber?.let {
            assertEquals(uiEmployee.phoneNumber, MOCK_FORMATTED_NUMBER)
        } ?: assertNull(uiEmployee.phoneNumber)

        when (response.type) {
            EmployeeResponse.Type.FULL_TIME -> assertEquals(uiEmployee.classification, FULL_TIME)
            EmployeeResponse.Type.PART_TIME -> assertEquals(uiEmployee.classification, PART_TIME)
            EmployeeResponse.Type.CONTRACTOR -> assertEquals(uiEmployee.classification, CONTRACT)
        }
    }
}

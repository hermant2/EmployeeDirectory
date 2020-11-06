package com.treyherman.employeedirectory.scenes.maindirectory.employee

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.treyherman.employeedirectory.mocks.ui.mockUIEmployee
import com.treyherman.employeedirectory.scenes.maindirectory.list.employee.EmployeeMvp
import com.treyherman.employeedirectory.scenes.maindirectory.list.employee.EmployeePresenter
import org.junit.Test

class EmployeePresenterTest {

    companion object {
        private const val MOCK_PHONE = "(333) 444-5566"
        private const val MOCK_BIO = "bio"
    }

    private val view = mock<EmployeeMvp.View>()
    private val presenter: EmployeeMvp.Presenter by lazy {
        EmployeePresenter(view)
    }

    private val mockEmployeeWithOptionals by lazy {
        mockUIEmployee(phoneNumber = MOCK_PHONE, bio = MOCK_BIO)
    }

    private val mockEmployeeWithoutOptionals by lazy {
        mockUIEmployee(phoneNumber = null, bio = null, photoUrl = null)
    }

    @Test
    fun onBind_withOptionals_displayEmployee() {
        presenter.onBind(mockEmployeeWithOptionals)
        verify(view).displayEmployeeInfo(
            mockEmployeeWithOptionals.nameAndTeam,
            mockEmployeeWithOptionals.email,
            mockEmployeeWithOptionals.classification,
            mockEmployeeWithOptionals.photoUrlSmall
        )

        verify(view).displayPhoneNumber(MOCK_PHONE)
        verify(view).displayBio(MOCK_BIO)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun onBind_withoutOptionals_displayEmployee() {
        presenter.onBind(mockEmployeeWithoutOptionals)
        verify(view).displayEmployeeInfo(
            mockEmployeeWithoutOptionals.nameAndTeam,
            mockEmployeeWithoutOptionals.email,
            mockEmployeeWithoutOptionals.classification,
            null
        )

        verify(view).hidePhoneNumber()
        verify(view).hideBio()
        verifyNoMoreInteractions(view)
    }
}
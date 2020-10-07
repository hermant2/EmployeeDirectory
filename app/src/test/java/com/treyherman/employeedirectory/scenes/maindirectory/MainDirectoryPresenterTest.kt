package com.treyherman.employeedirectory.scenes.maindirectory

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.*
import com.treyherman.employeedirectory.R
import com.treyherman.employeedirectory.mocks.response.mockEmployeeResponse
import com.treyherman.employeedirectory.mocks.ui.mockUIEmployee
import com.treyherman.employeedirectory.rest.model.response.employee.EmployeeResponseWrapper
import com.treyherman.employeedirectory.rest.service.EmployeeApiService
import com.treyherman.employeedirectory.rules.RxJavaTestRules
import com.treyherman.employeedirectory.scenes.maindirectory.mapper.EmployeeModelMapper
import com.treyherman.employeedirectory.scenes.maindirectory.model.DataSelectionType
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.reflect.Whitebox

@RunWith(PowerMockRunner::class)
class MainDirectoryPresenterTest {

    companion object {
        private const val NO_EMPLOYEES_FOUND = "no employees"
        private const val SOMETHING_WENT_WRONG = "wrong"
    }

    private val view = mock<MainDirectoryMvp.View>()
    private val resources = mock<Resources> {
        on { getString(R.string.no_employees_found) }.thenReturn(NO_EMPLOYEES_FOUND)
        on { getString(R.string.something_went_wrong) }.thenReturn(SOMETHING_WENT_WRONG)
    }
    private val employeeApiService = mock<EmployeeApiService>()
    private val employeeModelMapper = mock<EmployeeModelMapper>()

    private val presenter: MainDirectoryMvp.Presenter by lazy {
        MainDirectoryPresenter(view, resources, employeeApiService, employeeModelMapper)
    }

    @Rule
    val replaceSchedulers = RxJavaTestRules()

    @Test
    fun onCreate_defaultEmployeesOnceWithSuccess_displayEmployees() {
        val mockResponseWrapper = EmployeeResponseWrapper(listOf(mockEmployeeResponse()))
        whenever(employeeApiService.employeesOnce()).thenReturn(Single.just(mockResponseWrapper))
        val mockUIEmployees = listOf(mockUIEmployee())
        whenever(employeeModelMapper.mapEmployees(mockResponseWrapper)).thenReturn(mockUIEmployees)
        presenter.onCreate()

        verify(employeeModelMapper).mapEmployees(mockResponseWrapper)
        verify(employeeApiService).employeesOnce()
        verify(view).displayLoading()
        verify(view).hideLoading()
        verify(view).displayEmployees(mockUIEmployees)

        verifyNoMoreInteractions(employeeApiService, view, employeeModelMapper)
    }

    @Test
    fun onCreate_defaultEmployeesOnceWithEmptyEmployeesSuccess_displayEmptyContent() {
        val mockResponseWrapper = EmployeeResponseWrapper(emptyList())
        whenever(employeeApiService.employeesOnce()).thenReturn(Single.just(mockResponseWrapper))
        whenever(employeeModelMapper.mapEmployees(mockResponseWrapper)).thenReturn(emptyList())
        presenter.onCreate()

        verify(employeeModelMapper).mapEmployees(mockResponseWrapper)
        verify(employeeApiService).employeesOnce()
        verify(view).displayLoading()
        verify(view).hideLoading()
        verify(view).displayEmptyContent(NO_EMPLOYEES_FOUND)
        verify(resources).getString(R.string.no_employees_found)

        verifyNoMoreInteractions(employeeApiService, view, employeeModelMapper, resources)
    }

    @Test
    fun onCreate_defaultEmployeesOnceWithError_displayEmptyContent() {
        whenever(employeeApiService.employeesOnce()).thenReturn(Single.error(Throwable()))
        presenter.onCreate()

        verify(employeeApiService).employeesOnce()
        verify(view).displayLoading()
        verify(view).hideLoading()
        verify(view).displayEmptyContent(SOMETHING_WENT_WRONG)
        verify(resources).getString(R.string.something_went_wrong)

        verifyNoMoreInteractions(employeeApiService, view, employeeModelMapper, resources)
    }

    @Test
    fun onDestroy_employeeDisposableDisposed() {
        val disposable = mock<Disposable>()
        Whitebox.setInternalState(presenter, "employeesDisposable", disposable)
        presenter.onDestroy()
        verify(disposable).dispose()
        verifyNoMoreInteractions(disposable)
    }

    @Test
    fun onRefresh_defaultEmployeesOnceWithSuccess_updateEmployees() {
        val mockResponseWrapper = EmployeeResponseWrapper(listOf(mockEmployeeResponse()))
        whenever(employeeApiService.employeesOnce()).thenReturn(Single.just(mockResponseWrapper))
        val mockUIEmployees = listOf(mockUIEmployee())
        whenever(employeeModelMapper.mapEmployees(mockResponseWrapper)).thenReturn(mockUIEmployees)
        presenter.onRefresh(DataSelectionType.DEFAULT, emptyList())

        verify(employeeModelMapper).mapEmployees(mockResponseWrapper)
        verify(employeeApiService).employeesOnce()
        verify(view).hideLoading()
        verify(view).updateEmployees(eq(mockUIEmployees), any())

        verifyNoMoreInteractions(employeeApiService, view, employeeModelMapper)
    }

    @Test
    fun onRefresh_malformedEmployeesOnceWithError_displayEmptyContent() {
        whenever(employeeApiService.malformedEmployeesOnce()).thenReturn(Single.error(Throwable()))
        presenter.onRefresh(DataSelectionType.MALFORMED, emptyList())

        verify(employeeApiService).malformedEmployeesOnce()
        verify(view).hideLoading()
        verify(view).displayEmptyContent(SOMETHING_WENT_WRONG)
        verify(resources).getString(R.string.something_went_wrong)

        verifyNoMoreInteractions(employeeApiService, view, employeeModelMapper)
    }

    @Test
    fun onRefresh_emptyEmployeesOnceWithSuccess_displayEmptyContent() {
        val mockResponseWrapper = EmployeeResponseWrapper(emptyList())
        whenever(employeeApiService.emptyEmployeesOnce()).thenReturn(Single.just(mockResponseWrapper))
        whenever(employeeModelMapper.mapEmployees(mockResponseWrapper)).thenReturn(emptyList())
        presenter.onRefresh(DataSelectionType.EMPTY, emptyList())

        verify(employeeModelMapper).mapEmployees(mockResponseWrapper)
        verify(employeeApiService).emptyEmployeesOnce()
        verify(view).hideLoading()
        verify(view).displayEmptyContent(NO_EMPLOYEES_FOUND)
        verify(resources).getString(R.string.no_employees_found)

        verifyNoMoreInteractions(employeeApiService, view, employeeModelMapper)
    }

    @Test
    fun onTryAgainClicked_defaultEmployeesOnceWithSuccess_updateEmployees() {
        val mockResponseWrapper = EmployeeResponseWrapper(listOf(mockEmployeeResponse()))
        whenever(employeeApiService.employeesOnce()).thenReturn(Single.just(mockResponseWrapper))
        val mockUIEmployees = listOf(mockUIEmployee())
        whenever(employeeModelMapper.mapEmployees(mockResponseWrapper)).thenReturn(mockUIEmployees)
        presenter.onTryAgainClicked(DataSelectionType.DEFAULT, emptyList())

        verify(employeeModelMapper).mapEmployees(mockResponseWrapper)
        verify(employeeApiService).employeesOnce()
        verify(view).displayLoading()
        verify(view).hideLoading()
        verify(view).updateEmployees(eq(mockUIEmployees), any())

        verifyNoMoreInteractions(employeeApiService, view, employeeModelMapper)
    }

    @Test
    fun onTryAgainClicked_malformedEmployeesOnceWithError_displayEmptyContent() {
        whenever(employeeApiService.malformedEmployeesOnce()).thenReturn(Single.error(Throwable()))
        presenter.onTryAgainClicked(DataSelectionType.MALFORMED, emptyList())

        verify(employeeApiService).malformedEmployeesOnce()
        verify(view).displayLoading()
        verify(view).hideLoading()
        verify(view).displayEmptyContent(SOMETHING_WENT_WRONG)
        verify(resources).getString(R.string.something_went_wrong)

        verifyNoMoreInteractions(employeeApiService, view, employeeModelMapper, resources)
    }

    @Test
    fun onTryAgainClicked_emptyEmployeesOnceWithSuccess_displayEmptyContent() {
        val mockResponseWrapper = EmployeeResponseWrapper(emptyList())
        whenever(employeeApiService.emptyEmployeesOnce()).thenReturn(Single.just(mockResponseWrapper))
        whenever(employeeModelMapper.mapEmployees(mockResponseWrapper)).thenReturn(emptyList())
        presenter.onTryAgainClicked(DataSelectionType.EMPTY, emptyList())

        verify(employeeModelMapper).mapEmployees(mockResponseWrapper)
        verify(employeeApiService).emptyEmployeesOnce()
        verify(view).displayLoading()
        verify(view).hideLoading()
        verify(view).displayEmptyContent(NO_EMPLOYEES_FOUND)
        verify(resources).getString(R.string.no_employees_found)

        verifyNoMoreInteractions(employeeApiService, view, employeeModelMapper, resources)
    }

    @Test
    fun onDataTypeSelected_defaultEmployeesOnceWithSuccess_updateEmployees() {
        val mockResponseWrapper = EmployeeResponseWrapper(listOf(mockEmployeeResponse()))
        whenever(employeeApiService.employeesOnce()).thenReturn(Single.just(mockResponseWrapper))
        val mockUIEmployees = listOf(mockUIEmployee())
        whenever(employeeModelMapper.mapEmployees(mockResponseWrapper)).thenReturn(mockUIEmployees)
        presenter.onDataTypeSelected(DataSelectionType.DEFAULT, emptyList())

        verify(employeeModelMapper).mapEmployees(mockResponseWrapper)
        verify(employeeApiService).employeesOnce()
        verify(view).displayLoading()
        verify(view).hideLoading()
        verify(view).updateEmployees(eq(mockUIEmployees), any())

        verifyNoMoreInteractions(employeeApiService, view, employeeModelMapper)
    }

    @Test
    fun onDataTypeSelected_malformedEmployeesOnceWithError_displayEmptyContent() {
        whenever(employeeApiService.malformedEmployeesOnce()).thenReturn(Single.error(Throwable()))
        presenter.onDataTypeSelected(DataSelectionType.MALFORMED, emptyList())

        verify(employeeApiService).malformedEmployeesOnce()
        verify(view).displayLoading()
        verify(view).hideLoading()
        verify(view).displayEmptyContent(SOMETHING_WENT_WRONG)
        verify(resources).getString(R.string.something_went_wrong)

        verifyNoMoreInteractions(employeeApiService, view, employeeModelMapper, resources)
    }

    @Test
    fun onDataTypeSelected_emptyEmployeesOnceWithSuccess_displayEmptyContent() {
        val mockResponseWrapper = EmployeeResponseWrapper(emptyList())
        whenever(employeeApiService.emptyEmployeesOnce()).thenReturn(Single.just(mockResponseWrapper))
        whenever(employeeModelMapper.mapEmployees(mockResponseWrapper)).thenReturn(emptyList())
        presenter.onDataTypeSelected(DataSelectionType.EMPTY, emptyList())

        verify(employeeModelMapper).mapEmployees(mockResponseWrapper)
        verify(employeeApiService).emptyEmployeesOnce()
        verify(view).displayLoading()
        verify(view).hideLoading()
        verify(view).displayEmptyContent(NO_EMPLOYEES_FOUND)
        verify(resources).getString(R.string.no_employees_found)

        verifyNoMoreInteractions(employeeApiService, view, employeeModelMapper, resources)
    }
}

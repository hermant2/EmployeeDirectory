package com.treyherman.employeedirectory.scenes.maindirectory

import android.content.res.Resources
import com.treyherman.employeedirectory.R
import com.treyherman.employeedirectory.di.scope.ActivityScope
import com.treyherman.employeedirectory.extension.observeOnMain
import com.treyherman.employeedirectory.rest.model.response.employee.EmployeeResponseWrapper
import com.treyherman.employeedirectory.rest.service.EmployeeApiService
import com.treyherman.employeedirectory.scenes.maindirectory.mapper.EmployeeModelMapper
import com.treyherman.employeedirectory.scenes.maindirectory.model.DataSelectionType
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxkotlin.addTo

import javax.inject.Inject

@ActivityScope
class MainDirectoryPresenter @Inject constructor(
    private val view: MainDirectoryMvp.View,
    private val resources: Resources,
    private val employeeApiService: EmployeeApiService,
    private val employeeModelMapper: EmployeeModelMapper
) : MainDirectoryMvp.Presenter {

    private var employeesDisposable: Disposable? = null

    override fun onCreate() {
        view.displayLoading()
        subscribeToDefaultEmployees()
    }

    override fun onRefresh(dataSelection: DataSelectionType) {
        when (dataSelection) {
            DataSelectionType.DEFAULT -> subscribeToDefaultEmployees()
            DataSelectionType.MALFORMED -> subscribeToMalformedEmployees()
            DataSelectionType.EMPTY -> subscribeToEmptyEmployees()
        }
    }

    override fun onDestroy() {
        employeesDisposable?.dispose()
    }

    override fun onDataTypeSelected(dataSelection: DataSelectionType) {
        view.displayLoading()
        when (dataSelection) {
            DataSelectionType.DEFAULT -> subscribeToDefaultEmployees()
            DataSelectionType.MALFORMED -> subscribeToMalformedEmployees()
            DataSelectionType.EMPTY -> subscribeToEmptyEmployees()
        }
    }

    // region private
    private fun subscribeToDefaultEmployees() {
        employeesDisposable?.dispose()
        employeesDisposable = employeeApiService.employeesOnce()
            .map { employeeModelMapper.mapEmployees(it) }
            .observeOnMain()
            .subscribeWith(object : DisposableSingleObserver<List<UIEmployee>>() {
                override fun onSuccess(employees: List<UIEmployee>) {
                    view.hideLoading()
                    view.displayEmployees(employees)
                }

                override fun onError(e: Throwable) {
                    view.hideLoading()
                    view.displayErrorDialog(
                        resources.getString(R.string.oops),
                        resources.getString(R.string.something_went_wrong)
                    )
                }
            })
    }

    private fun subscribeToMalformedEmployees() {
        employeesDisposable?.dispose()
        employeesDisposable = employeeApiService.malformedEmployeesOnce()
            .map { employeeModelMapper.mapEmployees(it) }
            .observeOnMain()
            .subscribeWith(object : DisposableSingleObserver<List<UIEmployee>>() {
                override fun onSuccess(employees: List<UIEmployee>) {
                    view.hideLoading()
                    view.displayEmployees(employees)
                }

                override fun onError(e: Throwable) {
                    view.hideLoading()
                    view.displayErrorDialog(
                        resources.getString(R.string.oops),
                        resources.getString(R.string.something_went_wrong)
                    )
                }
            })
    }

    private fun subscribeToEmptyEmployees() {
        employeesDisposable?.dispose()
        employeesDisposable = employeeApiService.emptyEmployeesOnce()
            .map { employeeModelMapper.mapEmployees(it) }
            .observeOnMain()
            .subscribeWith(object : DisposableSingleObserver<List<UIEmployee>>() {
                override fun onSuccess(employees: List<UIEmployee>) {
                    view.hideLoading()
                    view.displayEmployees(employees)
                }

                override fun onError(e: Throwable) {
                    view.hideLoading()
                    view.displayErrorDialog(
                        resources.getString(R.string.oops),
                        resources.getString(R.string.something_went_wrong)
                    )
                }
            })
    }
    // endregion private
}

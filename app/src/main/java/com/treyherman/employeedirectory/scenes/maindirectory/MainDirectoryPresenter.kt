package com.treyherman.employeedirectory.scenes.maindirectory

import android.content.res.Resources
import com.treyherman.employeedirectory.R
import com.treyherman.employeedirectory.di.scope.ActivityScope
import com.treyherman.employeedirectory.extension.observeOnMain
import com.treyherman.employeedirectory.rest.model.response.employee.EmployeeResponseWrapper
import com.treyherman.employeedirectory.rest.service.EmployeeApiService
import com.treyherman.employeedirectory.scenes.maindirectory.mapper.EmployeeModelMapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxkotlin.addTo

import javax.inject.Inject

@ActivityScope
class MainDirectoryPresenter @Inject constructor(
    private val view: MainDirectoryMvp.View,
    private val resources: Resources,
    private val employeeApiService: EmployeeApiService,
    private val employeeModelMapper: EmployeeModelMapper,
    private val disposables: CompositeDisposable
) : MainDirectoryMvp.Presenter {

    override fun onCreate() {
        subscribeToEmployees()
    }

    override fun onRefresh() {

    }

    override fun onDestroy() {
        disposables.clear()
    }

    // region private
    private fun subscribeToEmployees() {
        employeeApiService.employeesOnce()
            .observeOnMain()
            .subscribeWith(object : DisposableSingleObserver<EmployeeResponseWrapper>() {
                override fun onSuccess(response: EmployeeResponseWrapper) {
                    val employees = employeeModelMapper.mapEmployees(response)
                    view.displayEmployees(employees)
                }

                override fun onError(e: Throwable) {
                    view.displayErrorDialog(
                        resources.getString(R.string.oops),
                        resources.getString(R.string.something_went_wrong)
                    )
                }
            }).addTo(disposables)
    }
    // endregion private
}

package com.treyherman.employeedirectory.scenes.maindirectory

import android.content.res.Resources
import androidx.recyclerview.widget.DiffUtil
import com.treyherman.employeedirectory.R
import com.treyherman.employeedirectory.di.scope.ActivityScope
import com.treyherman.employeedirectory.extension.observeOnMain
import com.treyherman.employeedirectory.extension.subscribeOnComputation
import com.treyherman.employeedirectory.rest.service.EmployeeApiService
import com.treyherman.employeedirectory.scenes.maindirectory.diff.EmployeeDiffCallback
import com.treyherman.employeedirectory.scenes.maindirectory.mapper.EmployeeModelMapper
import com.treyherman.employeedirectory.scenes.maindirectory.model.DataSelectionType
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver

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
        employeesDisposable?.dispose()
        employeesDisposable = defaultEmployeesOnce
            .observeOnMain()
            .subscribeWith(employeeObserver)
    }

    override fun onRefresh(dataSelection: DataSelectionType, currentEmployees: List<UIEmployee>) {
        when (dataSelection) {
            DataSelectionType.DEFAULT -> subscribeToRefreshedDefaultEmployees(currentEmployees)
            DataSelectionType.MALFORMED -> subscribeToRefreshedMalformedEmployees(currentEmployees)
            DataSelectionType.EMPTY -> subscribeToRefreshedEmptyEmployees(currentEmployees)
        }
    }

    override fun onDestroy() {
        employeesDisposable?.dispose()
    }

    override fun onTryAgainClicked(
        dataSelection: DataSelectionType,
        currentEmployees: List<UIEmployee>
    ) {
        view.displayLoading()
        when (dataSelection) {
            DataSelectionType.DEFAULT -> subscribeToRefreshedDefaultEmployees(currentEmployees)
            DataSelectionType.MALFORMED -> subscribeToRefreshedMalformedEmployees(currentEmployees)
            DataSelectionType.EMPTY -> subscribeToRefreshedEmptyEmployees(currentEmployees)
        }
    }

    override fun onDataTypeSelected(
        dataSelection: DataSelectionType,
        currentEmployees: List<UIEmployee>
    ) {
        view.displayLoading()
        when (dataSelection) {
            DataSelectionType.DEFAULT -> subscribeToRefreshedDefaultEmployees(currentEmployees)
            DataSelectionType.MALFORMED -> subscribeToRefreshedMalformedEmployees(currentEmployees)
            DataSelectionType.EMPTY -> subscribeToRefreshedEmptyEmployees(currentEmployees)
        }
    }

    // region private
    private fun subscribeToRefreshedDefaultEmployees(currentEmployees: List<UIEmployee>) {
        employeesDisposable?.dispose()
        employeesDisposable = defaultEmployeesOnce
            .flatMap { calculateEmployeeDiffOnce(currentEmployees, it) }
            .observeOnMain()
            .subscribeWith(employeesDiffResultObserver)
    }

    private fun subscribeToRefreshedMalformedEmployees(currentEmployees: List<UIEmployee>) {
        employeesDisposable?.dispose()
        employeesDisposable = malformedEmployeesOnce
            .flatMap { calculateEmployeeDiffOnce(currentEmployees, it) }
            .observeOnMain()
            .subscribeWith(employeesDiffResultObserver)
    }

    private fun subscribeToRefreshedEmptyEmployees(currentEmployees: List<UIEmployee>) {
        employeesDisposable?.dispose()
        employeesDisposable = emptyEmployeesOnce
            .flatMap { calculateEmployeeDiffOnce(currentEmployees, it) }
            .observeOnMain()
            .subscribeWith(employeesDiffResultObserver)
    }

    private fun calculateEmployeeDiffOnce(
        oldEmployees: List<UIEmployee>,
        newEmployees: List<UIEmployee>
    ): Single<Pair<List<UIEmployee>, DiffUtil.DiffResult>> {
        return Single.create<Pair<List<UIEmployee>, DiffUtil.DiffResult>> {
            if (!it.isDisposed) {
                val result =
                    DiffUtil.calculateDiff(EmployeeDiffCallback(oldEmployees, newEmployees), true)
                it.onSuccess(Pair(newEmployees, result))
            }
        }.subscribeOnComputation()
    }

    private val defaultEmployeesOnce
        get() = employeeApiService.employeesOnce()
            .map { employeeModelMapper.mapEmployees(it) }

    private val malformedEmployeesOnce
        get() = employeeApiService.malformedEmployeesOnce()
            .map { employeeModelMapper.mapEmployees(it) }

    private val emptyEmployeesOnce
        get() = employeeApiService.emptyEmployeesOnce()
            .map { employeeModelMapper.mapEmployees(it) }

    private val employeeObserver
        get() = object : DisposableSingleObserver<List<UIEmployee>>() {
            override fun onSuccess(employees: List<UIEmployee>) {
                view.hideLoading()
                when (employees.isNotEmpty()) {
                    true -> view.displayEmployees(employees)
                    false -> view.displayEmptyContent(resources.getString(R.string.no_employees_found))
                }
            }

            override fun onError(e: Throwable) {
                view.hideLoading()
                view.displayEmptyContent(resources.getString(R.string.something_went_wrong))
            }
        }

    private val employeesDiffResultObserver
        get() = object : DisposableSingleObserver<Pair<List<UIEmployee>, DiffUtil.DiffResult>>() {
            override fun onSuccess(employeeDiffPair: Pair<List<UIEmployee>, DiffUtil.DiffResult>) {
                view.hideLoading()
                when (employeeDiffPair.first.isNotEmpty()) {
                    true -> view.updateEmployees(employeeDiffPair.first, employeeDiffPair.second)
                    false -> view.displayEmptyContent(resources.getString(R.string.no_employees_found))
                }
            }

            override fun onError(e: Throwable) {
                view.hideLoading()
                view.displayEmptyContent(resources.getString(R.string.something_went_wrong))
            }
        }
    // endregion private
}

package com.treyherman.employeedirectory.scenes.employeedetail

import android.content.res.Resources
import android.os.Bundle
import com.treyherman.employeedirectory.R
import com.treyherman.employeedirectory.di.scope.ActivityScope
import com.treyherman.employeedirectory.scenes.employeedetail.EmployeeDetailActivity.Companion.KEY_EMPLOYEE_PARCELABLE
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee
import javax.inject.Inject

@ActivityScope
class EmployeeDetailPresenter @Inject constructor(
    private val view: EmployeeDetailMvp.View,
    private val resources: Resources
) : EmployeeDetailMvp.Presenter {

    private lateinit var employee: UIEmployee

    override fun onCreate(extras: Bundle?) {
        extras?.getParcelable<UIEmployee>(KEY_EMPLOYEE_PARCELABLE)?.let {
            employee = it
            when (it.isValid()) {
                true -> view.displayEmployee(it)
                false -> view.displayNonCancelableErrorDialog(resources.getString(R.string.something_went_wrong))
            }
        }
            ?: view.displayNonCancelableErrorDialog(resources.getString(R.string.something_went_wrong))
    }

    override fun onNonCancelableErrorDialogDismissed() {
        view.finish()
    }

    override fun onCallClicked() {
        employee.phoneNumber?.let {
            view.openDialScreen(it)
        }
    }

    override fun onEmailClicked() {
        view.openEmailScreen(employee.email)
    }
}

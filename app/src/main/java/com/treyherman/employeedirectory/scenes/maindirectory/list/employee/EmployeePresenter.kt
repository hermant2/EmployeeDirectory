package com.treyherman.employeedirectory.scenes.maindirectory.list.employee

import com.treyherman.employeedirectory.di.scope.ViewScope
import com.treyherman.employeedirectory.scenes.maindirectory.MainDirectoryMvp
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee
import javax.inject.Inject

@ViewScope
class EmployeePresenter @Inject constructor(
    private val view: EmployeeMvp.View,
    private val parentPresenter: MainDirectoryMvp.Presenter
) : EmployeeMvp.Presenter {

    private lateinit var employee: UIEmployee

    override fun onBind(employee: UIEmployee) {
        this.employee = employee
        view.displayEmployeeInfo(
            employee.nameAndTeam,
            employee.email,
            employee.classification,
            employee.photoUrlSmall
        )

        employee.phoneNumber?.let {
            view.displayPhoneNumber(it)
        } ?: view.hidePhoneNumber()

        employee.bio?.let {
            view.displayBio(it)
        } ?: view.hideBio()
    }

    override fun onEmployeeClicked() {
        parentPresenter.presentEmployeeDetails(employee)
    }
}

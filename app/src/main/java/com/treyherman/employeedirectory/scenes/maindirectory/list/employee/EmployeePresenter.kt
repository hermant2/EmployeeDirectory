package com.treyherman.employeedirectory.scenes.maindirectory.list.employee

import com.treyherman.employeedirectory.di.scope.ViewScope
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee
import javax.inject.Inject

@ViewScope
class EmployeePresenter @Inject constructor(
    private val view: EmployeeMvp.View
) : EmployeeMvp.Presenter {
    override fun onBind(employee: UIEmployee) {
        view.displayEmployeeInfo(employee.nameAndTeam, employee.email, employee.classification)

        employee.phoneNumber?.let {
            view.displayPhoneNumber(it)
        } ?: view.hidePhoneNumber()

        employee.bio?.let {
            view.displayBio(it)
        } ?: view.hideBio()
    }
}

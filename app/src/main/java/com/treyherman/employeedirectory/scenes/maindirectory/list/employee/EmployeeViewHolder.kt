package com.treyherman.employeedirectory.scenes.maindirectory.list.employee

import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee
import com.treyherman.employeedirectory.view.list.AbsViewHolder

class EmployeeViewHolder(
    private val employeeView: EmployeeView
) : AbsViewHolder<UIEmployee>(employeeView) {
    override fun onBind(data: UIEmployee) {
        employeeView.bind(data)
    }
}

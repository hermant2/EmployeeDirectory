package com.treyherman.employeedirectory.scenes.maindirectory.list

import android.content.Context
import android.view.View
import com.treyherman.employeedirectory.R
import com.treyherman.employeedirectory.scenes.maindirectory.list.employee.EmployeeSubcomponent
import com.treyherman.employeedirectory.scenes.maindirectory.list.employee.EmployeeView
import com.treyherman.employeedirectory.scenes.maindirectory.list.employee.EmployeeViewHolder
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee
import com.treyherman.employeedirectory.view.list.AbsItemsAdapter
import java.lang.IllegalStateException

class EmployeeAdapter(
    context: Context,
    private val employeeSubcomponentFactory: EmployeeSubcomponent.Factory
) : AbsItemsAdapter<UIEmployee, EmployeeViewHolder>(context) {
    override fun createViewHolderFromView(view: View, viewType: Int): EmployeeViewHolder {
        (view as? EmployeeView)?.let {
            it.inject(employeeSubcomponentFactory.create(it))
            return EmployeeViewHolder(it)
        } ?: throw IllegalStateException("unknown view")
    }

    override fun itemLayoutResource(viewType: Int): Int {
        return R.layout.item_employee
    }

    override fun itemViewType(position: Int): Int {
        return 0
    }

    override fun onBind(viewHolder: EmployeeViewHolder, data: UIEmployee, position: Int) {
        viewHolder.onBind(data)
    }
}

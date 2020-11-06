package com.treyherman.employeedirectory.scenes.maindirectory

import androidx.recyclerview.widget.DiffUtil
import com.treyherman.employeedirectory.scenes.maindirectory.model.DataSelectionType
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee

interface MainDirectoryMvp {
    interface View {
        fun displayEmployees(employees: List<UIEmployee>)

        fun updateEmployees(employees: List<UIEmployee>, diffResult: DiffUtil.DiffResult)

        fun displayLoading()

        fun hideLoading()

        fun displayEmptyContent(message: String)

        fun openEmployeeDetails(employee: UIEmployee)
    }

    interface Presenter {
        fun onCreate()

        fun onRefresh(dataSelection: DataSelectionType, currentEmployees: List<UIEmployee>)

        fun onDestroy()

        fun onTryAgainClicked(dataSelection: DataSelectionType, currentEmployees: List<UIEmployee>)

        fun onDataTypeSelected(dataSelection: DataSelectionType, currentEmployees: List<UIEmployee>)

        fun presentEmployeeDetails(employee: UIEmployee)
    }
}

package com.treyherman.employeedirectory.scenes.maindirectory

import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee

interface MainDirectoryMvp {
    interface View  {
        fun displayEmployees(employees: List<UIEmployee>)

        fun displayErrorDialog(title: String, message: String)

        fun displayLoading()

        fun hideLoading()
    }

    interface Presenter {
        fun onCreate()

        fun onRefresh()

        fun onDestroy()
    }
}

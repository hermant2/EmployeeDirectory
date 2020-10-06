package com.treyherman.employeedirectory.scenes.maindirectory.list.employee

import com.treyherman.employeedirectory.di.scope.ViewScope
import dagger.Binds
import dagger.Module

@Module
interface EmployeeBindModule {

    @Binds
    @ViewScope
    fun bindView(fragment: EmployeeView): EmployeeMvp.View
}
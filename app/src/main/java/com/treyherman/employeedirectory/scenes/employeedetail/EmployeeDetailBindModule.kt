package com.treyherman.employeedirectory.scenes.employeedetail

import com.treyherman.employeedirectory.di.scope.ActivityScope

import dagger.Binds
import dagger.Module

@Module
interface EmployeeDetailBindModule {

    @Binds
    @ActivityScope
    fun bindView(activity: EmployeeDetailActivity): EmployeeDetailMvp.View
}

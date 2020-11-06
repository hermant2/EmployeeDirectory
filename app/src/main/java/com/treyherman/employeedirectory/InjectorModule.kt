package com.treyherman.employeedirectory

import com.treyherman.employeedirectory.di.scope.ActivityScope
import com.treyherman.employeedirectory.scenes.employeedetail.EmployeeDetailActivity
import com.treyherman.employeedirectory.scenes.employeedetail.EmployeeDetailModule
import com.treyherman.employeedirectory.scenes.maindirectory.MainDirectoryActivity
import com.treyherman.employeedirectory.scenes.maindirectory.MainDirectoryModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface InjectorModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainDirectoryModule::class])
    fun contributesMainDirectoryActivity(): MainDirectoryActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [EmployeeDetailModule::class])
    fun contributesEmployeeDetailActivity(): EmployeeDetailActivity
}

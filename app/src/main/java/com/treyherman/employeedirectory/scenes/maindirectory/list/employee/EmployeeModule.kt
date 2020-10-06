package com.treyherman.employeedirectory.scenes.maindirectory.list.employee

import com.treyherman.employeedirectory.di.scope.ViewScope
import dagger.Module
import dagger.Provides

@Module(includes = [EmployeeBindModule::class])
class EmployeeModule {

    @Provides
    @ViewScope
    fun providePresenter(presenter: EmployeePresenter): EmployeeMvp.Presenter {
        return presenter
    }
}
package com.treyherman.employeedirectory.scenes.maindirectory.list.employee

import com.treyherman.employeedirectory.di.scope.ViewScope

import dagger.Subcomponent
import dagger.android.AndroidInjector

@ViewScope
@Subcomponent(modules = [EmployeeModule::class])
interface EmployeeSubcomponent : AndroidInjector<EmployeeView> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<EmployeeView>
}

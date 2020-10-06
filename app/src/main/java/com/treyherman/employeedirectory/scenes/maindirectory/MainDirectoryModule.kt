package com.treyherman.employeedirectory.scenes.maindirectory

import android.content.res.Resources
import com.treyherman.employeedirectory.di.scope.ActivityScope
import com.treyherman.employeedirectory.rest.service.EmployeeApiService
import com.treyherman.employeedirectory.rest.service.EmployeeApiServiceImpl
import com.treyherman.employeedirectory.scenes.maindirectory.list.employee.EmployeeSubcomponent
import com.treyherman.employeedirectory.scenes.maindirectory.mapper.EmployeeModelMapper
import com.treyherman.employeedirectory.scenes.maindirectory.mapper.EmployeeModelMapperImpl
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [MainDirectoryBindModule::class], subcomponents = [EmployeeSubcomponent::class])
class MainDirectoryModule {

    @Provides
    @ActivityScope
    fun providePresenter(
        view: MainDirectoryMvp.View,
        resources: Resources,
        employeeApiService: EmployeeApiService,
        employeeModelMapper: EmployeeModelMapper
    ): MainDirectoryMvp.Presenter {
        return MainDirectoryPresenter(
            view,
            resources,
            employeeApiService,
            employeeModelMapper,
            CompositeDisposable()
        )
    }

    @Provides
    @ActivityScope
    fun provideEmployeeApiService(employeeApiServiceImpl: EmployeeApiServiceImpl): EmployeeApiService {
        return employeeApiServiceImpl
    }

    @Provides
    @ActivityScope
    fun provideEmployeeModelMapper(modelMapper: EmployeeModelMapperImpl): EmployeeModelMapper {
        return modelMapper
    }
}

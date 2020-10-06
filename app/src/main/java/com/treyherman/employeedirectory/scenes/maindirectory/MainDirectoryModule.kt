package com.treyherman.employeedirectory.scenes.maindirectory

import android.content.Context
import android.content.res.Resources
import coil.ImageLoader
import com.treyherman.employeedirectory.di.scope.ActivityScope
import com.treyherman.employeedirectory.rest.service.EmployeeApiService
import com.treyherman.employeedirectory.rest.service.EmployeeApiServiceImpl
import com.treyherman.employeedirectory.scenes.maindirectory.list.employee.EmployeeSubcomponent
import com.treyherman.employeedirectory.scenes.maindirectory.mapper.EmployeeModelMapper
import com.treyherman.employeedirectory.scenes.maindirectory.mapper.EmployeeModelMapperImpl
import com.treyherman.employeedirectory.view.image.ImageLoaderProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [MainDirectoryBindModule::class], subcomponents = [EmployeeSubcomponent::class])
class MainDirectoryModule {

    @Provides
    @ActivityScope
    fun providePresenter(presenter: MainDirectoryPresenter): MainDirectoryMvp.Presenter {
        return presenter
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

    @Provides
    @ActivityScope
    fun provideImageLoader(
        imageLoaderProvider: ImageLoaderProvider,
        context: Context
    ): ImageLoader {
        return imageLoaderProvider.provide(context)
    }
}

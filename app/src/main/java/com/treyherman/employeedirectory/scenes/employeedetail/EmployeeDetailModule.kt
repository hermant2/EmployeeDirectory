package com.treyherman.employeedirectory.scenes.employeedetail

import android.content.Context
import coil.ImageLoader
import com.treyherman.employeedirectory.di.scope.ActivityScope
import com.treyherman.employeedirectory.view.image.ImageLoaderProvider
import dagger.Module
import dagger.Provides

@Module(includes = [EmployeeDetailBindModule::class])
class EmployeeDetailModule {

    @Provides
    @ActivityScope
    fun providePresenter(presenter: EmployeeDetailPresenter): EmployeeDetailMvp.Presenter {
        return presenter
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

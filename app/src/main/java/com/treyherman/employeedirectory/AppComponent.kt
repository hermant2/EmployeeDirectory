package com.treyherman.employeedirectory

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        AppModule::class,
        PersistenceModule::class,
        AndroidModule::class,
        InjectorModule::class
    ]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindApplication(application: EmployeeDirectoryApplication): Builder

        fun build(): AppComponent
    }

    fun inject(application: EmployeeDirectoryApplication)
}

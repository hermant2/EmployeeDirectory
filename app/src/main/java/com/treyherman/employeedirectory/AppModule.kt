package com.treyherman.employeedirectory

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: EmployeeDirectoryApplication): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideApplication(application: EmployeeDirectoryApplication): Application {
        return application
    }
}

package com.treyherman.employeedirectory

import android.app.Application
import android.content.Context
import com.treyherman.employeedirectory.manager.phonenumber.PhoneNumberFormatManager
import com.treyherman.employeedirectory.manager.phonenumber.PhoneNumberFormatManagerImpl
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

    @Provides
    @Singleton
    fun providePhoneNumberFormatManager(): PhoneNumberFormatManager {
        return PhoneNumberFormatManagerImpl()
    }
}

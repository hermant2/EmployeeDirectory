package com.treyherman.employeedirectory

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule {
    @Singleton
    @Provides
    fun provideResources(context: Context): Resources {
        return context.resources
    }
}

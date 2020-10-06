package com.treyherman.employeedirectory.scenes.maindirectory

import com.treyherman.employeedirectory.di.scope.ActivityScope

import dagger.Binds
import dagger.Module

@Module
interface MainDirectoryBindModule {

    @Binds
    @ActivityScope
    fun bindView(activity: MainDirectoryActivity): MainDirectoryMvp.View
}

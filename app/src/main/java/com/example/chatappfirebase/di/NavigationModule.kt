package com.example.chatappfirebase.di

import com.example.chatappfirebase.navigate.AppNavigationDispatcher
import com.example.chatappfirebase.navigate.AppNavigationHandler
import com.example.chatappfirebase.navigate.AppNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {
    @Binds
    fun bindAppNavigator(impl: AppNavigationDispatcher): AppNavigator

    @Binds
    fun bindAppNavHandler(impl: AppNavigationDispatcher): AppNavigationHandler
}

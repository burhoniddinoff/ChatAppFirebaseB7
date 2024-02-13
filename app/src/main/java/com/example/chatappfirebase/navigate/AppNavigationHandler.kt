package com.example.chatappfirebase.navigate

import com.example.chatappfirebase.navigate.AppNavigation
import kotlinx.coroutines.flow.Flow

interface AppNavigationHandler {
    val buffer: Flow<AppNavigation>
}
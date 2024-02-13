package com.example.chatappfirebase.navigate

import androidx.navigation.NavDirections

interface AppNavigator{

    suspend fun navigateTo(direction: NavDirections)
    suspend fun backTo()

}
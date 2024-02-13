package com.example.chatappfirebase.navigate

import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigationDispatcher @Inject constructor() : AppNavigator, AppNavigationHandler {

    override val buffer = MutableSharedFlow<AppNavigation>()

    private suspend fun navigate(navigate: AppNavigation) {
        buffer.emit(navigate)
    }

    override suspend fun navigateTo(direction: NavDirections) = navigate {
        navigate(direction)
    }

    override suspend fun backTo() = navigate {
        navigateUp()
    }

}
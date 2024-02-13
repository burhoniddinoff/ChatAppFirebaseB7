package com.example.chatappfirebase.presentation.viewModel

import kotlinx.coroutines.flow.MutableSharedFlow

interface LoginViewModel {
    val successFlow: MutableSharedFlow<String>
    val errorMessage: MutableSharedFlow<String>

    fun logIn(email: String, password: String)
}
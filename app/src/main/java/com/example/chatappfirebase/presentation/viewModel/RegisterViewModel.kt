package com.example.chatappfirebase.presentation.viewModel

import com.example.chatappfirebase.data.ChatData
import kotlinx.coroutines.flow.MutableSharedFlow

interface RegisterViewModel {
    val successFlow: MutableSharedFlow<String>
    val errorMessage: MutableSharedFlow<String>

    fun register(name: String, email: String, password: String)
}
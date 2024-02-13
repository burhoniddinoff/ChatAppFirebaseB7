package com.example.chatappfirebase.presentation.viewModel

import com.example.chatappfirebase.data.ChatData
import kotlinx.coroutines.flow.MutableSharedFlow

interface ChatViewModel {

    val successFlow: MutableSharedFlow<String>
    val errorMessage: MutableSharedFlow<String>


}
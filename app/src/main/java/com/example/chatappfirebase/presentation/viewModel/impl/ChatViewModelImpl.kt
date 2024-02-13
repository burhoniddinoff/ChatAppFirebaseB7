package com.example.chatappfirebase.presentation.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatappfirebase.data.ChatData
import com.example.chatappfirebase.domain.AppRepositoryImpl
import com.example.chatappfirebase.presentation.viewModel.ChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatViewModelImpl @Inject constructor(
    private val repository: AppRepositoryImpl,
) : ViewModel(), ChatViewModel {

    override val errorMessage: MutableSharedFlow<String> = repository.errorFlow
    override val successFlow = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)



}
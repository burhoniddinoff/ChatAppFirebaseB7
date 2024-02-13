package com.example.chatappfirebase.presentation.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatappfirebase.domain.AppRepositoryImpl
import com.example.chatappfirebase.presentation.viewModel.RegisterViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModelImpl @Inject constructor(
    private val repository: AppRepositoryImpl,
) : ViewModel(), RegisterViewModel {

    override val successFlow = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val errorMessage: MutableSharedFlow<String> = repository.errorFlow

    override fun register(name: String, email: String, password: String) {
        repository.registerUser(name, email, password).onEach {
            it.onSuccess {
                successFlow.emit("Register success bro!")
            }
            it.onFailure { thr ->
                errorMessage.emit(thr.message.toString())
            }
        }.launchIn(viewModelScope)
    }


}
package com.example.chatappfirebase.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chatappfirebase.R
import com.example.chatappfirebase.databinding.ScreenRegisterBinding
import com.example.chatappfirebase.presentation.viewModel.RegisterViewModel
import com.example.chatappfirebase.presentation.viewModel.impl.RegisterViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RegisterScreen : Fragment(R.layout.screen_register) {

    private val binding by viewBinding(ScreenRegisterBinding::bind)
    private val viewModel: RegisterViewModel by viewModels<RegisterViewModelImpl>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) Toast.makeText(requireContext(), "Email or password error!", Toast.LENGTH_SHORT).show()
            else register(name, email, password)
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.errorMessage.onEach {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)

        viewModel.successFlow.onEach {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)

    }

    private fun register(name: String, email: String, password: String) {
        viewModel.register(name, email, password)
        findNavController().navigate(RegisterScreenDirections.actionRegisterScreenToUserScreen())
    }

}
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
import com.example.chatappfirebase.databinding.ScreenLogInBinding
import com.example.chatappfirebase.presentation.viewModel.LoginViewModel
import com.example.chatappfirebase.presentation.viewModel.impl.LoginViewModelImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LogInScreen : Fragment(R.layout.screen_log_in) {

    private val binding by viewBinding(ScreenLogInBinding::bind)
    private val viewModel: LoginViewModel by viewModels<LoginViewModelImpl>()
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) Toast.makeText(requireContext(), "Email or password error!", Toast.LENGTH_SHORT).show()
            else logIN(email, password)
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(LogInScreenDirections.actionLogInScreenToRegisterScreen())
        }

        viewModel.successFlow.onEach {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)

        viewModel.errorMessage.onEach {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)

    }

    private fun logIN(email: String, password: String) {
//        viewModel.logIn(email, password)

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigate(LogInScreenDirections.actionLogInScreenToUserScreen())
            } else Toast.makeText(requireContext(), "log in error bro!", Toast.LENGTH_SHORT).show()

        }
    }

}
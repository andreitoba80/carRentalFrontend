package com.example.carrentalfrontend.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.carrentalfrontend.databinding.FragmentRegisterBinding
import com.example.carrentalfrontend.domain.model.dto.RegisterUserDto
import com.example.carrentalfrontend.extensions.addIconFocusChangeColorEffect
import com.example.carrentalfrontend.extensions.isEmpty
import com.example.carrentalfrontend.extensions.isValidEmail
import com.example.carrentalfrontend.extensions.trimmedText
import com.example.carrentalfrontend.ui.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {
    private val viewModel by viewModel<RegisterViewModel>()

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRegisterBinding.inflate(layoutInflater).let {
        binding = it
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.fullnameEditText.addIconFocusChangeColorEffect()
        binding.usernameEditText.addIconFocusChangeColorEffect()
        binding.emailEditText.addIconFocusChangeColorEffect()
        binding.passwordEditText.addIconFocusChangeColorEffect()
        binding.confirmPasswordEditText.addIconFocusChangeColorEffect()

        binding.registerButton.setOnClickListener {
            val fullName = binding.fullnameEditText.trimmedText()
            val username = binding.usernameEditText.trimmedText()
            val email = binding.emailEditText.trimmedText()
            val password = binding.passwordEditText.trimmedText()
            val confirmPassword = binding.confirmPasswordEditText.trimmedText()

            if (binding.fullnameEditText.isEmpty() || binding.usernameEditText.isEmpty() || binding.emailEditText.isEmpty()
                || binding.passwordEditText.isEmpty() || binding.confirmPasswordEditText.isEmpty()
            ) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!binding.emailEditText.isValidEmail()) {
                Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = RegisterUserDto(
                fullName = fullName,
                username = username,
                email = email,
                password = password
            )

            viewModel.register(user)
        }

        binding.loginButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObservers() {
        viewModel.registerSuccessfully.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(
                    context,
                    "Account has been created successfully",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(
                    context,
                    "There was an error while creating your account:\n",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
package com.example.carrentalfrontend.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.carrentalfrontend.R
import com.example.carrentalfrontend.databinding.FragmentLoginBinding
import com.example.carrentalfrontend.domain.model.dto.LoginDto
import com.example.carrentalfrontend.extensions.addIconFocusChangeColorEffect
import com.example.carrentalfrontend.ui.viewmodel.LoginViewModel
import com.example.carrentalfrontend.util.handle
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private val viewModel by viewModel<LoginViewModel>()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoginBinding.inflate(layoutInflater).let {
        binding = it
        binding.root
    }

//        FragmentLoginBinding.inflate(layoutInflater).also {
//            binding = it
//        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()
        initObservers()
    }

    private fun addListeners() {
        binding.usernameEditText.addIconFocusChangeColorEffect()
        binding.passwordEditText.addIconFocusChangeColorEffect()

        binding.loginButton.setOnClickListener {
            viewModel.login(
                LoginDto(
                    username = binding.usernameEditText.text.toString(),
                    password = binding.passwordEditText.text.toString()
                )
            )
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun showErrorMessage(errorMessage: String) {
        Toast.makeText(
            context,
            errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun initObservers() {
        viewModel.userCredentials.observe(viewLifecycleOwner) {
            it.handle(
                onSuccess = { user ->
                    viewModel.updateValue(user)
                    if (user.isAdmin) {
                        findNavController().navigate(R.id.action_loginFragment_to_adminScreenFragment)
                    } else {
                        findNavController().navigate(R.id.action_loginFragment_to_mainScreenFragment)
                    }
                },
                onFailure = ::showErrorMessage
            )
        }
    }
}
package com.example.carrentalfrontend.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.carrentalfrontend.databinding.FragmentEditUserBinding
import com.example.carrentalfrontend.domain.model.dto.UpdateUserDto
import com.example.carrentalfrontend.domain.model.entity.User
import com.example.carrentalfrontend.extensions.addIconFocusChangeColorEffect
import com.example.carrentalfrontend.extensions.isEmpty
import com.example.carrentalfrontend.extensions.isValidEmail
import com.example.carrentalfrontend.extensions.trimmedText
import com.example.carrentalfrontend.ui.viewmodel.EditUserViewModel
import com.example.carrentalfrontend.util.logDebugError
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditUserFragment : Fragment() {
    private val viewmodel by viewModel<EditUserViewModel>()

    private lateinit
    var binding: FragmentEditUserBinding

    private lateinit var userDetails: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentEditUserBinding.inflate(layoutInflater).let {
        binding = it
        binding.root
    }.also {
        arguments?.getSerializable("SELECTED_USER_DATA_KEY")?.let {
            logDebugError("User details from EditUserFragment $it")
            userDetails = it as User
            binding.fullnameEditText.setText(it.fullName)
            binding.usernameEditText.setText(it.username)
            binding.emailEditText.setText(it.email)
            if (userDetails.isAdmin) {
                binding.isAdminButton.isChecked = true
                binding.isNotAdminButton.isChecked = false
            } else {
                binding.isAdminButton.isChecked = false
                binding.isNotAdminButton.isChecked = true
            }
        }
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

        binding.editButton.setOnClickListener {
            val fullName = binding.fullnameEditText.trimmedText()
            val username = binding.usernameEditText.trimmedText()
            val email = binding.emailEditText.trimmedText()
            val password = binding.passwordEditText.trimmedText()

            if (binding.fullnameEditText.isEmpty() || binding.usernameEditText.isEmpty() || binding.emailEditText.isEmpty() || binding.passwordEditText.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!binding.emailEditText.isValidEmail()) {
                Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = UpdateUserDto(
                username = username,
                password = password,
                email = email,
                fullName = fullName,
                isAdmin = binding.isAdminButton.isChecked
            )

            viewmodel.updateUser(userDetails.id, user)
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObservers() {
        viewmodel.editSuccess.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(
                    context,
                    "User Credentials have been updated successfully",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "There was an error while updating User Credentials",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
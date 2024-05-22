package com.example.carrentalfrontend.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.carrentalfrontend.databinding.FragmentManageCarListingBinding

class ManageCarListingFragment : Fragment() {
    private lateinit var binding: FragmentManageCarListingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentManageCarListingBinding.inflate(layoutInflater).let {
        binding = it
        binding.root
    }.also {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.addCarButton.setOnClickListener {
            
        }
    }
}
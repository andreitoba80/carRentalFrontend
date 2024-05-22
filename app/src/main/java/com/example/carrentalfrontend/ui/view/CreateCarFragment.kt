package com.example.carrentalfrontend.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carrentalfrontend.databinding.FragmentCreateCarBinding

class CreateCarFragment : Fragment() {
    private lateinit var binding: FragmentCreateCarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCreateCarBinding.inflate(layoutInflater).let {
        binding = it
        binding.root
    }.also {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
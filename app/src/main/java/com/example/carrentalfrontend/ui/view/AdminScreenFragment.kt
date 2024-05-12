package com.example.carrentalfrontend.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.carrentalfrontend.R
import com.example.carrentalfrontend.databinding.FragmentAdminScreenBinding
import com.example.carrentalfrontend.ui.viewmodel.AdminScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminScreenFragment : Fragment() {
    private val viewModel by viewModel<AdminScreenViewModel>()
    private lateinit var binding: FragmentAdminScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAdminScreenBinding.inflate(layoutInflater).let {
        binding = it
        binding.root
    }.also {
        initUi()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.manageUsersButton.root.setOnClickListener {
            findNavController().navigate(R.id.action_adminScreenFragment_to_userListFragment)
        }

        binding.manageCarBrandsListingButton.root.setOnClickListener {
            findNavController().navigate(R.id.action_adminScreenFragment_to_manageCarBrandsFragment)
        }
    }

    private fun initUi() {
        binding.manageUsersButton.itemIcon.setImageResource(R.drawable.ic_people)
        binding.manageUsersButton.itemText.text = "Manage Users"

        binding.manageCarsListingButton.itemIcon.setImageResource(R.drawable.ic_car)
        binding.manageCarsListingButton.itemText.text = "Cars Listing"

        binding.manageCarBrandsListingButton.itemIcon.setImageResource(R.drawable.ic_brand)
        binding.manageCarBrandsListingButton.itemText.text = "Car Brands"

        binding.manageBookingsButton.itemIcon.setImageResource(R.drawable.ic_booking)
        binding.manageBookingsButton.itemText.text = "Car Bookings"

        binding.reportsButton.itemIcon.setImageResource(R.drawable.ic_report)
        binding.reportsButton.itemText.text = "Reports"

        viewModel.getUserFullName()
        viewModel.userFullName.observe(viewLifecycleOwner) {
            binding.welcomeUserMessage.text = "Welcome " + it
        }

    }
}
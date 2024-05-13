package com.example.carrentalfrontend.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carrentalfrontend.R
import com.example.carrentalfrontend.databinding.FragmentHomeBinding
import com.example.carrentalfrontend.domain.model.data.CarBrand
import com.example.carrentalfrontend.domain.model.entity.Car
import com.example.carrentalfrontend.ui.adapter.CarBrandsListAdapter
import com.example.carrentalfrontend.ui.viewmodel.HomeFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment() : Fragment() {
    private val viewModel by viewModel<HomeFragmentViewModel>()

    private lateinit var binding: FragmentHomeBinding

    private lateinit var carBrandList: ArrayList<CarBrand>
    private lateinit var carsList: ArrayList<Car>
    private lateinit var carBrandsListAdapter: CarBrandsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(layoutInflater).let {
        binding = it
        binding.root
    }.also {
        initCarBrandsRecyclerView()
        initObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()
    }

    private fun addListeners() {
        binding.logoutButton.setOnClickListener {
            viewModel.deleteUserCredentials()
            findNavController().popBackStack()
        }
    }

    private fun initObserver() {
        viewModel.carList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {

            }
        }

        viewModel.carBrandList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                carBrandList = it
                addDataToList(it)
            }
        }
    }

    private fun initCarBrandsRecyclerView() {
        binding.brandsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            carBrandList = ArrayList()
            viewModel.fetchCarBrands()
        }
    }

    private fun addDataToList(carBrands: ArrayList<CarBrand>) {
        carBrandsListAdapter = CarBrandsListAdapter(carBrands, R.layout.brands_item, {}, {})
        binding.brandsRecyclerView.adapter = carBrandsListAdapter
    }
}
package com.example.carrentalfrontend.ui.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.carrentalfrontend.R
import com.example.carrentalfrontend.databinding.FragmentManageCarBrandsBinding
import com.example.carrentalfrontend.domain.model.data.CarBrands
import com.example.carrentalfrontend.ui.adapter.CarBrandsListAdapter
import com.example.carrentalfrontend.ui.viewmodel.ManageCarBrandsViewModel
import com.example.carrentalfrontend.util.logDebugError
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageCarBrandsFragment : Fragment() {
    private val viewModel by viewModel<ManageCarBrandsViewModel>()

    private lateinit var binding: FragmentManageCarBrandsBinding
    private lateinit var carBrandsListAdapter: CarBrandsListAdapter
    private lateinit var carBrandsList: ArrayList<CarBrands>
    private lateinit var createCarBrandDialogView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentManageCarBrandsBinding.inflate(layoutInflater).let {
        binding = it
        binding.root
    }.also {
        initCarBrandsListRecyclerView()
        initObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack();
        }

        binding.carBrandsRefreshLayout.setOnRefreshListener {
            binding.carBrandsRefreshLayout.isRefreshing = false
            viewModel.fetchCarBrands()
        }

        binding.addCarBrandButton.setOnClickListener {
            logDebugError("I have clicked Create Car Brand Button")
            showCreateBrandDialog()
        }
    }

    private fun showCreateBrandDialog() {
        val builder = context?.let {
            AlertDialog.Builder(it)
        }?.apply {
            setTitle("Add Car Brand")
            createCarBrandDialogView =
                layoutInflater.inflate(R.layout.add_car_brand_dialog_layout, null)
            setView(createCarBrandDialogView)

            setPositiveButton("Create") { dialog: DialogInterface?, which: Int ->

            }
        }
        createCarBrandDialogView.findViewById<Button>(R.id.upload_icon_button).setOnClickListener {
            logDebugError("I have clicked Upload Button")
            // TODO: Create upload file method
        }
        val dialog = builder?.create()
        dialog?.show()
    }

    private fun initObserver() {
        viewModel.carBrandsList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                carBrandsList = it
                addDataToList(it)
            }
        }
    }

    private fun initCarBrandsListRecyclerView() {
        binding.carBrandsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            carBrandsList = ArrayList()
            viewModel.fetchCarBrands()
        }
    }

    private fun addDataToList(carBrands: ArrayList<CarBrands>) {
        carBrandsListAdapter = CarBrandsListAdapter(carBrands, R.layout.car_brand_list_item)
        binding.carBrandsRecyclerView.adapter = carBrandsListAdapter
    }
}
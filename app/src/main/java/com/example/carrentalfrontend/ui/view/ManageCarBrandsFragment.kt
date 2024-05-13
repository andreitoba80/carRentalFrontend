package com.example.carrentalfrontend.ui.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.carrentalfrontend.R
import com.example.carrentalfrontend.databinding.FragmentManageCarBrandsBinding
import com.example.carrentalfrontend.domain.model.data.CarBrand
import com.example.carrentalfrontend.ui.adapter.CarBrandsListAdapter
import com.example.carrentalfrontend.ui.viewmodel.ManageCarBrandsViewModel
import com.example.carrentalfrontend.util.logDebugError
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageCarBrandsFragment : Fragment() {
    private val viewModel by viewModel<ManageCarBrandsViewModel>()

    private lateinit var binding: FragmentManageCarBrandsBinding
    private lateinit var carBrandsListAdapter: CarBrandsListAdapter
    private lateinit var carBrandList: ArrayList<CarBrand>
    private lateinit var createCarBrandDialogView: View

    private lateinit var uploadImageUri: Uri
    private lateinit var galleryImage: ActivityResultLauncher<String>

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

        galleryImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                uploadImageUri = it
                Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
            }
        }
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

            setPositiveButton("Create") { dialog, _ ->
                logDebugError("I have clicked \"Create\" Button")
                val brandEditText =
                    createCarBrandDialogView.findViewById<EditText>(R.id.car_brand_name_edit_text)
                val brandName = brandEditText.text.toString()

                val fileUri = uploadImageUri

                viewModel.addCarBrand(brandName, fileUri)
            }
        }
        createCarBrandDialogView.findViewById<Button>(R.id.upload_icon_button).setOnClickListener {
            logDebugError("I have clicked Upload Button")

            galleryImage.launch("image/*")
        }
        val dialog = builder?.create()
        dialog?.show()
    }

    private fun initObserver() {
        viewModel.carBrandList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                carBrandList = it
                addDataToList(it)
            }
        }
    }

    private fun initCarBrandsListRecyclerView() {
        val gridLayoutManager = GridLayoutManager(context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }
        binding.carBrandsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
        }
        carBrandList = ArrayList()
        viewModel.fetchCarBrands()
    }

    private fun addDataToList(carBrands: ArrayList<CarBrand>) {
        carBrandsListAdapter = CarBrandsListAdapter(
            carBrands,
            R.layout.car_brand_list_item,
            {
                logDebugError("[ManageCarBrandsFragment] onEditClick: $it")
            }, {
                logDebugError("[ManageCarBrandsFragment] onDeleteClick: $it")
            })
        binding.carBrandsRecyclerView.adapter = carBrandsListAdapter
    }
}
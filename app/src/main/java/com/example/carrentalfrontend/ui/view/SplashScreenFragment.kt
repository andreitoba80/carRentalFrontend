package com.example.carrentalfrontend.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.carrentalfrontend.R
import com.example.carrentalfrontend.databinding.FragmentSplashScreenBinding
import com.example.carrentalfrontend.ui.viewmodel.SplashScreenViewModel
import com.example.carrentalfrontend.util.Event
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    private val viewModel by viewModel<SplashScreenViewModel>()
    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSplashScreenBinding.inflate(layoutInflater).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        viewModel.checkUserLoggedIn()
    }

    private fun initObservers() {
        viewModel.splashScreenTimer.observe(viewLifecycleOwner) {
            Log.e("TOBA", "observer event = $it")
            if (it is Event.Success) {
                val destinationBuilder =
                    if (it.data.isLoggedIn)
                        if (it.data.isAdmin)
                            R.id.action_splashScreenFragment_to_adminScreenFragment
                        else
                            R.id.action_splashScreenFragment_to_mainScreenFragment
                    else R.id.action_splashScreenFragment_to_loginFragment
                findNavController().navigate(destinationBuilder)
            }
        }
    }
}
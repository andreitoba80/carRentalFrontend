package com.example.carrentalfrontend.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carrentalfrontend.databinding.FragmentMainScreenBinding


class MainScreenFragment : Fragment() {
    private lateinit var binding: FragmentMainScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainScreenBinding.inflate(layoutInflater).let {
        binding = it
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val bottomBarBackground = binding.bottomAppBar.background as MaterialShapeDrawable
//        bottomBarBackground.shapeAppearanceModel =
//            bottomBarBackground.shapeAppearanceModel.toBuilder()
//                .setTopRightCorner(CornerFamily.ROUNDED, 25f)
//                .setTopLeftCorner(CornerFamily.ROUNDED, 25f)
//                .build()
    }
}
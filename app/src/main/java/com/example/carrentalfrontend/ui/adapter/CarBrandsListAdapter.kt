package com.example.carrentalfrontend.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carrentalfrontend.R
import com.example.carrentalfrontend.domain.model.data.CarBrand
import com.example.carrentalfrontend.util.logDebugError

class CarBrandsListAdapter(
    private val carBrandList: List<CarBrand>,
    private val layoutId: Int
) :
    RecyclerView.Adapter<CarBrandsListAdapter.CarBrandsViewHolder>() {

    class CarBrandsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carBrandImageView: ImageView = itemView.findViewById(R.id.car_brand_icon)
        val carBrandName: TextView = itemView.findViewById(R.id.car_brand_name)
        val editButton: ImageView? = itemView.findViewById(R.id.edit_button)
        val deleteButton: ImageView? = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarBrandsViewHolder =
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false).let {
            CarBrandsViewHolder(it)
        }

    override fun onBindViewHolder(holder: CarBrandsViewHolder, position: Int) {
        val currentCarBrand = carBrandList[position]
        Glide.with(holder.itemView.context)
            .load(currentCarBrand.brandImageUrl) // Assuming brandImageUrl is a URL string
            .into(holder.carBrandImageView)
//        holder.carBrandImageView.setImageResource(brand.brandImageUrl)
        holder.carBrandName.text = currentCarBrand.brand

        if (layoutId == R.layout.car_brand_list_item) {
            holder.editButton?.setOnClickListener {
                logDebugError("I have clicked Edit Button on Car Brand Item")
            }
            holder.deleteButton?.setOnClickListener {
                logDebugError("I have clicked Delete Button on Car Brand Item")
            }
        }
    }

    override fun getItemCount(): Int {
        return carBrandList.size
    }

}
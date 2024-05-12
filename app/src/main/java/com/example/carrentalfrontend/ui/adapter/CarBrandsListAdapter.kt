package com.example.carrentalfrontend.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carrentalfrontend.R
import com.example.carrentalfrontend.domain.model.data.CarBrands
import com.example.carrentalfrontend.util.logDebugError

class CarBrandsListAdapter(
    private val carBrandsList: List<CarBrands>,
    private val layoutId: Int
) :
    RecyclerView.Adapter<CarBrandsListAdapter.CarBrandsViewHolder>() {

    class CarBrandsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carBrandImageView: ImageView = itemView.findViewById(R.id.car_brand_icon)
        val carBrandName: TextView = itemView.findViewById(R.id.car_brand_name)
        val editButton: ImageView = itemView.findViewById(R.id.edit_button)
        val deleteButton: ImageView = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarBrandsViewHolder =
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false).let {
            CarBrandsViewHolder(it)
        }

    override fun onBindViewHolder(holder: CarBrandsViewHolder, position: Int) {
        val brand = carBrandsList[position]
        holder.carBrandImageView.setImageResource(brand.carBrandIcon)
        holder.carBrandName.text = brand.carBrandName

        if (layoutId == R.layout.car_brand_list_item) {
            holder.editButton.setOnClickListener {
                logDebugError("I have clicked Edit Button on Car Brand Item")
            }
            holder.deleteButton.setOnClickListener {
                logDebugError("I have clicked Delete Button on Car Brand Item")
            }
        }
    }

    override fun getItemCount(): Int {
        return carBrandsList.size
    }

}
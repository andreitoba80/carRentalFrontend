package com.example.carrentalfrontend.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carrentalfrontend.R
import com.example.carrentalfrontend.domain.model.entity.User

class UserListAdapter(
    private val userList: List<User>,
    private val onEditClick: (User) -> Unit,
    private val onDeleteClick: (User) -> Unit
) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userFullName: TextView = itemView.findViewById(R.id.user_fullname_label)
        val username: TextView = itemView.findViewById(R.id.username_label)
        val editButton: ImageView = itemView.findViewById(R.id.edit_button)
        val deleteButton: ImageView = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder =
        LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false).let {
            UserListViewHolder(it)
        }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = userList[position]
        holder.userFullName.text = user.fullName
        holder.username.text = user.username

        holder.editButton.setOnClickListener {
            onEditClick(user)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(user)
        }
    }

    override fun getItemCount(): Int = userList.size
}
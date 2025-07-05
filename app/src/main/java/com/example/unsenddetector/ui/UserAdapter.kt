package com.example.unsenddetector.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.unsenddetector.databinding.UserItemBinding

class UsersAdapter(
    private val onUserClick: (String) -> Unit
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val users = mutableListOf<String>()

    fun submitList(newUsers: List<String>) {
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userName: String) {
            binding.userTXTName.text = userName
            binding.root.setOnClickListener {
                onUserClick(userName)
            }
        }
    }
}

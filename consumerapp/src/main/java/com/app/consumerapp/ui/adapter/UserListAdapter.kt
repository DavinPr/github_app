package com.app.consumerapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.consumerapp.data.User
import com.app.consumerapp.databinding.UserItemBinding
import com.bumptech.glide.Glide

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private val list = ArrayList<User>()

    fun setData(list: List<User>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    val getList = list

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder =
        UserViewHolder(
            UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = list[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = list.size

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.userName.text = user.name ?: user.username
            binding.userUsername.text = user.username
            Glide.with(itemView.context)
                .load(user.avatar)
                .into(binding.userAvatar)
        }
    }
}
package com.app.githubmobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.coremodule.domain.usecase.model.User
import com.app.githubmobile.databinding.UserItemBinding
import com.bumptech.glide.Glide

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private val list = ArrayList<User>()
    var onClickItem: ((String) -> Unit)? = null

    fun getSwipedData(swipedPosition: Int): User {
        return list[swipedPosition]
    }

    fun setData(list: List<User>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

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

        init {
            itemView.setOnClickListener {
                onClickItem?.invoke(list[adapterPosition].username)
            }
        }
    }
}
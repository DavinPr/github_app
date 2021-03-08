package com.app.githubmobile.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.coremodule.domain.usecase.model.User
import com.app.githubmobile.databinding.UserItemBinding
import com.app.githubmobile.helper.setImage

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private val list = ArrayList<User>()
    var onClickItem: ((User) -> Unit)? = null

    fun setData(list: List<User>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserListAdapter.UserViewHolder =
        UserViewHolder(
            UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: UserListAdapter.UserViewHolder, position: Int) {
        val user = list[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = list.size

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.userName.text = user.name
            binding.userUsername.text = user.username
            user.avatar?.let { setImage(itemView.context, binding.userAvatar, it) }
        }

        init {
            itemView.setOnClickListener {
                onClickItem?.invoke(list[adapterPosition])
            }
        }
    }
}
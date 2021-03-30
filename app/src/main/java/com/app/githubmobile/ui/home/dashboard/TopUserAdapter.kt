package com.app.githubmobile.ui.home.dashboard

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import com.app.coremodule.domain.usecase.model.User
import com.app.githubmobile.R
import com.app.githubmobile.databinding.TopUserItemBinding
import com.app.githubmobile.helper.shortNumberDisplay
import com.bumptech.glide.Glide

class TopUserAdapter : RecyclerView.Adapter<TopUserAdapter.UserViewHolder>() {

    private val list = ArrayList<User>()
    var onClickItem: ((String) -> Unit)? = null

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
            TopUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = list[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = list.size

    inner class UserViewHolder(private val binding: TopUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.topUserName.text = user.name ?: user.name
            binding.topUserUsername.text = user.username
            val followers = SpannableStringBuilder()
                .bold { append(user.followers.shortNumberDisplay()) }
                .append(" ${itemView.resources.getString(R.string.followers)}")
            binding.topUserFollowers.text = followers

            val following = SpannableStringBuilder()
                .bold { append(user.following.shortNumberDisplay()) }
                .append(" ${itemView.resources.getString(R.string.following)}")
            binding.topUserFollowing.text = following
            Glide.with(itemView.context)
                .load(user.avatar)
                .into(binding.topUserAvatar)
        }

        init {
            itemView.setOnClickListener {
                onClickItem?.invoke(list[adapterPosition].username)
            }
        }
    }
}
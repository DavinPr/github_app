package com.app.githubmobile.ui.home.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.coremodule.domain.usecase.model.Recent
import com.app.githubmobile.databinding.RecentItemBinding

class RecentListAdapter : RecyclerView.Adapter<RecentListAdapter.RecentViewHolder>() {

    private val list = ArrayList<Recent>()
    var onClickItem: ((String) -> Unit)? = null

    fun setData(list: List<Recent>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentListAdapter.RecentViewHolder =
        RecentViewHolder(
            RecentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecentListAdapter.RecentViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = list.size

    inner class RecentViewHolder(private val binding: RecentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recent: Recent) {
            binding.recentUsername.text = recent.username
        }

        init {
            itemView.setOnClickListener {
                onClickItem?.invoke(list[adapterPosition].username)
            }
        }
    }
}
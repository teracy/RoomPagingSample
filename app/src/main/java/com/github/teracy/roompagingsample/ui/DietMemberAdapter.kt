package com.github.teracy.roompagingsample.ui

import android.app.Application
import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.teracy.roompagingsample.R
import com.github.teracy.roompagingsample.data.paging.DietMember
import com.github.teracy.roompagingsample.databinding.ItemDietMemberBinding

class DietMemberAdapter(private val application: Application, private val listener: OnDietMemberClickListener)
    : PagedListAdapter<DietMember, DietMemberAdapter.ViewHolder>(ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_diet_member, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindTo(item)
        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemDietMemberBinding = DataBindingUtil.bind(itemView)

        fun bindTo(dietMember: DietMember?) {
            binding.item = dietMember
        }
    }

    companion object {
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<DietMember>() {
            override fun areItemsTheSame(oldItem: DietMember, newItem: DietMember): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: DietMember, newItem: DietMember): Boolean = oldItem == newItem
        }
    }
}
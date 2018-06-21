package com.github.teracy.roompagingsample.ui.speech

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.teracy.roompagingsample.R
import com.github.teracy.roompagingsample.data.paging.Speech
import com.github.teracy.roompagingsample.databinding.ItemSpeechBinding

class SpeechAdapter(private val context: Context, private val listener: OnSpeechClickListener)
    : PagedListAdapter<Speech, SpeechAdapter.ViewHolder>(ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_speech, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindTo(item)
        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemSpeechBinding = DataBindingUtil.bind(itemView)

        fun bindTo(speech: Speech?) {
            binding.item = speech
        }
    }

    companion object {
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<Speech>() {
            override fun areItemsTheSame(oldItem: Speech, newItem: Speech): Boolean = (oldItem.session == newItem.session && oldItem.speechOrder == newItem.speechOrder)
            override fun areContentsTheSame(oldItem: Speech, newItem: Speech): Boolean = oldItem == newItem
        }
    }
}
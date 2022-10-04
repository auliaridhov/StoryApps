package com.harvdev.storyapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harvdev.storyapp.R
import com.harvdev.storyapp.databinding.StoriesItemBinding
import com.harvdev.storyapp.model.Story

class StoriesAdapter (private val ctx: Context) :
    PagingDataAdapter<Story, StoriesAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var views: StoriesItemBinding

    private var listStory: List<Story> = emptyList()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.stories_item, viewGroup, false)
        views = StoriesItemBinding.bind(view)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = getItem(position)
        if (data != null) {
            holder.bind(data, onItemClickCallback)
        }

    }

    fun getView() : StoriesItemBinding{
        return views
    }

    override fun getItemCount(): Int = listStory.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.iv_item_photo)
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvDesc: TextView = itemView.findViewById(R.id.story_desc)

        fun bind(story: Story, listener: OnItemClickCallback) {
            Glide.with(itemView.context)
                .load(story.photoUrl)
                .into(imgPhoto)
            tvName.text = story.name
            tvDesc.text = story.description
            itemView.setOnClickListener { listener.onItemClicked(story) }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Story)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
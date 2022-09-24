package com.harvdev.storyapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harvdev.storyapp.R
import com.harvdev.storyapp.databinding.StoriesItemBinding
import com.harvdev.storyapp.model.Story

class StoriesAdapter (private val ctx: Context) :
    RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
//    lateinit var imgPhoto: ImageView
//    lateinit var tvName: TextView
//    lateinit var tvDesc: TextView
    private lateinit var views: StoriesItemBinding

    private var listStory: List<Story> = emptyList()

    fun updateData(list: List<Story>){
        this.listStory = list
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.stories_item, viewGroup, false)
        views = StoriesItemBinding.bind(view)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = listStory[position]
        Glide.with(ctx)
            .load(story.photoUrl)
            .into(holder.imgPhoto)
        holder.tvName.text = story.name
        holder.tvDesc.text = story.description
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listStory[holder.adapterPosition]) }

    }

    fun getView() : StoriesItemBinding{
        return views
    }

    override fun getItemCount(): Int = listStory.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.iv_item_photo)
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvDesc: TextView = itemView.findViewById(R.id.story_desc)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Story)
    }
}
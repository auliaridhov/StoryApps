package com.harvdev.storyapp.ui.detailstory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.harvdev.storyapp.R
import com.harvdev.storyapp.databinding.DetailStoryFragmentBinding
import com.harvdev.storyapp.model.Story

class DetailStoryFragment : Fragment() {

    companion object {
        fun newInstance() = DetailStoryFragment()
    }

    private lateinit var viewModel: DetailStoryViewModel
    private lateinit var imageStory: ImageView
    private lateinit var nameText: TextView
    private lateinit var descText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_story_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DetailStoryFragmentBinding.bind(view)

        val args = arguments
        val story: Story? = args?.getSerializable("DETAIL") as Story?

        initBinding(binding)
        initData(story)

    }

    private fun initBinding(binding: DetailStoryFragmentBinding){
        imageStory = binding.ivDetailPhoto
        nameText = binding.tvDetailName
        descText = binding.tvDetailDescription
    }

    private fun initData(story: Story?){
        Glide.with(requireContext())
            .load(story?.photoUrl)
            .into(imageStory)

        nameText.text = story?.name
        descText.text = story?.description
    }

}
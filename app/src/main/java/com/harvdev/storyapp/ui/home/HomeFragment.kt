package com.harvdev.storyapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.harvdev.storyapp.R
import com.harvdev.storyapp.adapter.StoriesAdapter
import com.harvdev.storyapp.databinding.FragmentHomeBinding
import com.harvdev.storyapp.model.Story
import com.harvdev.storyapp.ui.utils.safeNavigate
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text

class HomeFragment : Fragment() {

    companion object {
        private const val FRAGMENT_ID = R.id.navigation_home
    }

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var rvStories: RecyclerView
    private lateinit var loadingView: ProgressBar
    private lateinit var adapter: StoriesAdapter
    private lateinit var addButton: FloatingActionButton
    private lateinit var btnLogout: ImageView
    private lateinit var textUsername: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentHomeBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)

        initBinding(binding)
        initViewModel()
        initObserver()
        initRecyclerView()
        initOnClickListener()


        homeViewModel.getStories()
        homeViewModel.getProfile()
    }

    private fun initBinding(binding: FragmentHomeBinding){
        rvStories = binding.rvStories
        loadingView = binding.progressBar
        addButton = binding.addStoryFab
        btnLogout = binding.actionLogout
        textUsername = binding.usernameText
    }

    private fun initViewModel(){
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private fun initRecyclerView(){
        val llm = LinearLayoutManager(requireContext())
        llm.orientation = LinearLayoutManager.VERTICAL
        rvStories.layoutManager = llm

        adapter = StoriesAdapter(requireContext())

        adapter.setOnItemClickCallback(object : StoriesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Story) {
                val extras = FragmentNavigatorExtras(
                    adapter.getView().ivItemPhoto to "image",
                    adapter.getView().tvItemName to "name",
                    adapter.getView().storyDesc to "description"
                )
                val args = Bundle()
                args.putSerializable(
                    "DETAIL",
                    data
                )
                safeNavigate(FRAGMENT_ID, R.id.action_navigation_home_to_navigation_detail, args, null, extras)
            }
        })
        rvStories.adapter = adapter

    }

    private fun initObserver(){
        homeViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it){
                loadingView.visibility = View.VISIBLE
            } else {
                loadingView.visibility = View.GONE
            }
        })
        homeViewModel.listStories.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
        })
        homeViewModel.profile.observe(viewLifecycleOwner, Observer {
            textUsername.text = it.name
        })
    }

    private fun initOnClickListener(){
        addButton.setOnClickListener {
            safeNavigate(FRAGMENT_ID, R.id.action_navigation_home_to_navigation_story)
        }
        btnLogout.setOnClickListener {
            homeViewModel.logout()
            safeNavigate(FRAGMENT_ID, R.id.action_navigation_home_to_navigation_login)
        }
    }

}
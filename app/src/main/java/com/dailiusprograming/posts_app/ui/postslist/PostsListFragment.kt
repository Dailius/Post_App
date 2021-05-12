package com.dailiusprograming.posts_app.ui.postslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dailiusprograming.posts_app.R
import com.dailiusprograming.posts_app.data.model.Posts
import com.dailiusprograming.posts_app.databinding.FragmentPostsListBinding
import com.dailiusprograming.posts_app.ui.dialogs.ErrorDialogFragment
import com.dailiusprograming.posts_app.util.DataResult
import com.dailiusprograming.posts_app.util.TAG
import com.dailiusprograming.posts_app.util.TAG_DIALOG
import com.dailiusprograming.posts_app.viewmodel.PostsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsListFragment : Fragment() {

    private val viewModel: PostsListViewModel by activityViewModels()
    private var _binding: FragmentPostsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val postsListAdapter = PostsListAdapter { posts -> adapterOnClick(posts) }

        binding.apply {
            swipeRefresh = swipeRefreshLayout
            recyclerView = postsRecyclerView

            recyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = postsListAdapter
            }

            viewModel.postsList.observe(viewLifecycleOwner, { result ->
                when (result.status) {
                    DataResult.Status.LOADING -> {
                        recyclerView.isVisible = false
                        progressBar.isVisible = true
                        Log.i(TAG, "Fragment: LOADING")
                    }
                    DataResult.Status.ERROR -> {
                        recyclerView.isVisible = true
                        progressBar.isVisible = false
                        result.data?.let { postsListAdapter.setData(it) }
                        swipeRefreshLayout.isRefreshing = false
                        result.message?.let { showErrorDialogFragment(it) }
                        Log.i(TAG, "Fragment: ERROR")
                    }
                    DataResult.Status.SUCCESS -> {
                        recyclerView.isVisible = true
                        progressBar.isVisible = false
                        result.data?.let { postsListAdapter.setData(it) }
                        swipeRefreshLayout.isRefreshing = false
                        Log.i(TAG, "Fragment: SUCCESS")
                    }
                }
            })

            swipeRefresh.setOnRefreshListener {
                viewModel.fetchPosts(DataResult.Status.REFRESH)
            }
        }
    }

    private fun adapterOnClick(posts: Posts) {
        viewModel.fetchPost(postId = posts.postsId, userId = posts.userId)
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_postsListFragment_to_postDetailFragment)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showErrorDialogFragment(message: String) {
        val dialogFragment = ErrorDialogFragment(message)
        dialogFragment.isCancelable = false
        dialogFragment.show(childFragmentManager, TAG_DIALOG)
    }
}
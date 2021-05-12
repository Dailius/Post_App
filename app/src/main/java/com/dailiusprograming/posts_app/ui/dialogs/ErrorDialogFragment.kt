package com.dailiusprograming.posts_app.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.dailiusprograming.posts_app.databinding.FragmentDialogErrorBinding
import com.dailiusprograming.posts_app.util.DataResult
import com.dailiusprograming.posts_app.viewmodel.PostsListViewModel

class ErrorDialogFragment(private val message: String) : DialogFragment() {

    private val viewModel: PostsListViewModel by activityViewModels()
    private var _binding: FragmentDialogErrorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            txtMessage.text = message
            btnRefresh.setOnClickListener {
                dismiss()
                viewModel.fetchPosts(DataResult.Status.INIT)
            }
            btnCancel.setOnClickListener { dismiss() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
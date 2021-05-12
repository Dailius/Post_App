package com.dailiusprograming.posts_app.ui.postdetail

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.CircleCropTransformation
import com.dailiusprograming.posts_app.R
import com.dailiusprograming.posts_app.databinding.FragmentPostDetailBinding
import com.dailiusprograming.posts_app.util.USER_PHOTO
import com.dailiusprograming.posts_app.viewmodel.PostsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailFragment : Fragment() {

    private val viewModel: PostsListViewModel by activityViewModels()
    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            viewModel.postDetail.observe(viewLifecycleOwner, { postDetail ->
                txtTitleName.text = postDetail.data?.title
                txtTitleBody.apply {
                    text = postDetail.data?.body
                    movementMethod = ScrollingMovementMethod()
                }
            })

            viewModel.postAndUser.observe(viewLifecycleOwner, { result ->

                val userDetails = result.data?.user
                if (userDetails != null) {
                    txtName.text = userDetails.personFullName
                    txtCity.text = userDetails.address.city
                    txtCompany.text = userDetails.company.companyName

                    val photoPath = USER_PHOTO + (userDetails.userId)

                    ivwUserPhoto.load(photoPath) {
                        crossfade(true)
                        crossfade(500)
                        placeholder(R.drawable.people_placeholder)
                        error(R.drawable.people_placeholder)
                        fallback(R.drawable.people_placeholder)
                        size(ViewSizeResolver(ivwUserPhoto))
                        transformations(CircleCropTransformation())
                    }
                }

                val postDetails = result.data?.postsDetails
                if (postDetails != null) {
                    txtTitleName.text = postDetails.title
                    txtTitleBody.apply {
                        text = postDetails.body
                        movementMethod = ScrollingMovementMethod()
                    }
                }

                // GLIDE crashes with 'Pixel 2 API R' but works pretty good with 'Huawei P20' and 'Pixel 2 API 27'
//                try {
//                    GlideApp.with(this@PostDetailFragment)
//                        .load(photoPath)
//                        .error(R.drawable.people_placeholder)
//                        .fallback(R.drawable.people_placeholder)
//                        .transition(DrawableTransitionOptions.withCrossFade())
//                        .override(400, 400)
//                        .centerCrop()
//                        .thumbnail(0.01f)
//                        .into(imgUserPhoto)
//                } catch (e: Exception) {
//                    Log.i(TAG, "onCreateView: ${e.message}")
//                    Log.i(TAG, "onCreateView: ${e}")
//                }

                // Pretty good, without crashes but slow
//                Picasso.get()
//                    .load(photoPath)
//                    .placeholder(R.drawable.people_placeholder)
//                    .error(R.drawable.people_placeholder)
//                    .resize(200, 200)
//                    .centerCrop()
//                    .into(imgUserPhoto)
            })
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
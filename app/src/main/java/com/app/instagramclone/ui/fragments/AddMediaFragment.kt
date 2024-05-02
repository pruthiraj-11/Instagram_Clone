package com.app.instagramclone.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.instagramclone.R
import com.app.instagramclone.databinding.FragmentAddMediaBinding
import com.app.instagramclone.ui.activities.post.AddPostActivity
import com.app.instagramclone.ui.activities.post.AddReelsActivity

class AddMediaFragment : Fragment() {
    private var _binding: FragmentAddMediaBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMediaBinding.inflate(inflater, container, false)

        binding.addPost.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),AddPostActivity::class.java))
        }

        binding.addReels.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),AddReelsActivity::class.java))
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
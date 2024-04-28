package com.app.instagramclone.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.instagramclone.R
import com.app.instagramclone.databinding.FragmentDashboardBinding
import com.app.instagramclone.databinding.FragmentMyReelsBinding

class MyReelsFragment : Fragment() {
    private var _binding: FragmentMyReelsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyReelsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
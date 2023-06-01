package com.example.aisle.ui.dashboard.notes

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aisle.R
import com.example.aisle.databinding.FragmentNotesBinding
import com.example.aisle.databinding.FragmentOtpScreenBinding
import com.example.aisle.ui.dashboard.DashboardActivity
import com.example.aisle.ui.dashboard.notes.adapter.LikesAdapter
import com.example.aisle.ui.userverification.UserVerificationViewModel
import com.example.aisle.ui.userverification.UserVerificationViewModelFactory
import com.example.aisle.utils.Resource

class NotesFragment : Fragment() {

    lateinit var  recyclerView: RecyclerView

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelProviderFactory = NotesViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NotesViewModel::class.java]
        bindView()
        bindObserver()
        recyclerView =view.findViewById(R.id.rvLikes)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
    }

    private fun bindObserver() {
        viewModel.getNotesResult.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    recyclerView.adapter = response.data?.likes?.profiles?.let { LikesAdapter(it) }
                    context?.let {
                        _binding?.ivProfileImage?.let { it1 ->
                            Glide.with(it)
                                .load(response.data?.invites?.profiles?.get(0)?.photos?.get(0)?.photo)
                                .centerCrop()
                                .into(it1)
                        }
                    }
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
            }
        })
    }

    private fun bindView() {
        viewModel.getNotes("")
    }
}
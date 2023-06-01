package com.example.aisle.ui.userverification.enternumber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.aisle.R
import com.example.aisle.databinding.FragmentEnterNumberBinding
import com.example.aisle.ui.userverification.UserVerificationActivity
import com.example.aisle.ui.userverification.UserVerificationViewModel
import com.example.aisle.utils.Resource

class EnterNumberFragment : Fragment() {

    private var _binding: FragmentEnterNumberBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: UserVerificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEnterNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as UserVerificationActivity).viewModel
        bindView()
        bindObservers()
    }

    private fun bindObservers() {
        viewModel.verifyPhoneNumberResult.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    findNavController().navigate(
                        R.id.action_enterNumberFragment_to_otpScreenFragment
                    )
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
            }
        })
    }

    private fun bindView() {
        _binding?.btnContinue?.setOnClickListener {
            viewModel.verifyPhoneNumber("")
        }
    }
}
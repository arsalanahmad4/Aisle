package com.example.aisle.ui.userverification.enternumber

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.blankj.utilcode.util.SnackbarUtils
import com.example.aisle.databinding.FragmentEnterNumberBinding
import com.example.aisle.ui.userverification.UserVerificationActivity
import com.example.aisle.ui.userverification.UserVerificationViewModel
import com.example.aisle.utils.Resource
import com.example.aisle.utils.hideKeyboard
import com.example.aisle.utils.isValidIndianMobileNumber

class EnterNumberFragment : Fragment() {

    private var _binding: FragmentEnterNumberBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: UserVerificationViewModel

    private val safeArgs by navArgs<EnterNumberFragmentArgs>()

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
                    _binding?.loading?.visibility = View.GONE
                    val actionPhoneToVerifyOtp = EnterNumberFragmentDirections.actionEnterNumberFragmentToOtpScreenFragment(_binding?.etMobileNumber?.text.toString())
                    findNavController().navigate(actionPhoneToVerifyOtp)
                }
                is Resource.Error -> {
                    _binding?.loading?.visibility = View.GONE
                    showSnackBar(response.message)
                }
                is Resource.Loading -> {
                    _binding?.loading?.visibility = View.VISIBLE
                }
            }
        })


    }

    private fun bindView() {
        _binding?.etMobileNumber?.setText(safeArgs.number)
        _binding?.btnContinue?.setOnClickListener {
            hideKeyboard(requireContext(),_binding?.etMobileNumber)
            if(_binding?.etMobileNumber?.text?.toString()?.isValidIndianMobileNumber() == true){
                viewModel.verifyPhoneNumber(_binding?.etMobileNumber?.text?.toString()?:"")
            }else{
                showSnackBar("Please enter a valid number")
            }
        }
    }

    private fun showSnackBar(message: String?) {
        message?.let {
            _binding?.rootLayout?.let { it1 ->
                SnackbarUtils.with(it1)
                    .setMessage(message)
                    .setMessageColor(Color.WHITE)
                    .show()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
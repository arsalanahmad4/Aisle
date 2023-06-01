package com.example.aisle.ui.userverification.verifyotp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.aisle.R
import com.example.aisle.databinding.FragmentOtpScreenBinding
import com.example.aisle.ui.userverification.UserVerificationActivity
import com.example.aisle.ui.userverification.UserVerificationViewModel
import com.example.aisle.utils.Constants
import com.example.aisle.utils.Resource

class OtpScreenFragment : Fragment() {

    private var _binding: FragmentOtpScreenBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: UserVerificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOtpScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as UserVerificationActivity).viewModel
        bindView()
        bindObservers()
    }

    private fun bindObservers() {
        viewModel.verifyOtpResult.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {

                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
            }
        })
    }

    private fun bindView() {
        startOTPSMSTimer()
        _binding?.btnContinue?.setOnClickListener {
            viewModel.verifyOtp("", "")
        }
    }

    private fun startOTPSMSTimer() {
        val timer = object : CountDownTimer(Constants.otpWaitTimer, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (context != null) {
                    _binding?.otpTimer?.text = String.format(
                        resources.getString(R.string.verify_otp_wait_timer_text),
                        millisUntilFinished / 1000
                    )
                }
            }

            override fun onFinish() {
                if (context != null) {

                }

            }
        }
        timer.start()
    }
}
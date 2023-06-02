package com.example.aisle.ui.userverification.verifyotp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.blankj.utilcode.util.SnackbarUtils
import com.example.aisle.R
import com.example.aisle.databinding.FragmentOtpScreenBinding
import com.example.aisle.ui.dashboard.DashboardActivity
import com.example.aisle.ui.userverification.UserVerificationActivity
import com.example.aisle.ui.userverification.UserVerificationViewModel
import com.example.aisle.ui.userverification.enternumber.EnterNumberFragmentDirections
import com.example.aisle.utils.*


class OtpScreenFragment : Fragment() {

    private var _binding: FragmentOtpScreenBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: UserVerificationViewModel

    private val safeArgs by navArgs<OtpScreenFragmentArgs>()

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
                    _binding?.loading?.visibility = View.GONE
                    AppDataHelper(requireContext()).save(Constants.AUTH_KEY,response.data?.token)
                    val intent = Intent(context, DashboardActivity::class.java)
                    // Add the flags to clear the task
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

                    startActivity(intent)
                }
                is Resource.Error -> {
                    _binding?.loading?.visibility = View.GONE
                    hideKeyboard(requireContext(),_binding?.etOtp)
                    showSnackBar(response.message)
                }
                is Resource.Loading -> {
                    _binding?.loading?.visibility = View.VISIBLE
                }
            }
        })

        viewModel.verifyPhoneNumberResult.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    _binding?.loading?.visibility = View.GONE
                    startOTPSMSTimer()
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
        startOTPSMSTimer()
        _binding?.tvEnteredPhoneNumber?.text = "+91 "+safeArgs.number
        _binding?.btnContinue?.setOnClickListener {
            hideKeyboard(requireContext(),_binding?.etOtp)
            if(_binding?.etOtp?.text?.toString()?.isValidOTP() == true){
                viewModel.verifyOtp(safeArgs.number, _binding?.etOtp?.text?.toString()?:"")
            }else{
                showSnackBar("Please enter a valid otp")
            }
        }
        _binding?.icEditNumber?.setOnClickListener {
            val actionOtpToEnterNumber = OtpScreenFragmentDirections.actionOtpScreenFragmentToEnterNumberFragment(safeArgs.number)
            findNavController().navigate(actionOtpToEnterNumber)
        }
    }

    private fun startOTPSMSTimer() {
        val timer = object : CountDownTimer(Constants.otpWaitTimer, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (context != null) {
                    _binding?.otpTimer?.setOnClickListener {  }
                    _binding?.otpTimer?.text = String.format(
                        resources.getString(R.string.verify_otp_wait_timer_text),
                        millisUntilFinished / 1000
                    )
                }
            }

            override fun onFinish() {
                if (context != null) {
                    _binding?.otpTimer?.text = "Resend"
                    _binding?.otpTimer?.setOnClickListener {
                        viewModel.verifyPhoneNumber(safeArgs.number)
                    }
                }

            }
        }
        timer.start()
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
}
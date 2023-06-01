package com.example.aisle.ui.userverification

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aisle.AisleApplication
import com.example.aisle.data.AisleRepository
import com.example.aisle.data.model.PhoneNumberApiRequest
import com.example.aisle.data.model.PhoneNumberApiResponse
import com.example.aisle.data.model.VerifyOtpRequest
import com.example.aisle.data.model.VerifyOtpResponse
import com.example.aisle.utils.Resource
import com.example.aisle.utils.hasInternetConnection
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class UserVerificationViewModel(private val aisleRepository: AisleRepository, app: Application) :
    AndroidViewModel(app) {

    private val _verifyPhoneNumberResult = MutableLiveData<Resource<PhoneNumberApiResponse>>()
    val verifyPhoneNumberResult: LiveData<Resource<PhoneNumberApiResponse>> =
        _verifyPhoneNumberResult

    private val _verifyOtpResult = MutableLiveData<Resource<VerifyOtpResponse>>()
    val verifyOtpResult: LiveData<Resource<VerifyOtpResponse>> = _verifyOtpResult

    fun verifyPhoneNumber(number: String) {
        viewModelScope.launch {
            safeVerifyNumberCall(number)
        }
    }

    fun verifyOtp(number: String, otp: String) {
        viewModelScope.launch {
            safeVerifyOtpCall(number, otp)
        }
    }

    private suspend fun safeVerifyNumberCall(number: String) {
        _verifyPhoneNumberResult.postValue(Resource.Loading())
        try {
            val connectivityManager = getApplication<AisleApplication>().getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            if (hasInternetConnection(connectivityManager)) {
                val phoneNumberRequest = PhoneNumberApiRequest("+919876543212")
                val response = aisleRepository.verifyPhoneNumber(phoneNumberRequest)
                _verifyPhoneNumberResult.postValue(verifyPhoneNumberApiResponse(response))
            } else {
                _verifyPhoneNumberResult.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _verifyPhoneNumberResult.postValue(Resource.Error("Network Failure"))
                else -> _verifyPhoneNumberResult.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun verifyPhoneNumberApiResponse(response: Response<PhoneNumberApiResponse>): Resource<PhoneNumberApiResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeVerifyOtpCall(number: String, otp: String) {
        _verifyOtpResult.postValue(Resource.Loading())
        try {
            val connectivityManager = getApplication<AisleApplication>().getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            if (hasInternetConnection(connectivityManager)) {
                val verifyOtpRequest = VerifyOtpRequest("+919876543212", "1234")
                val response = aisleRepository.verifyOtp(verifyOtpRequest)
                _verifyOtpResult.postValue(handleVerifyOtpResponse(response))
            } else {
                _verifyOtpResult.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _verifyOtpResult.postValue(Resource.Error("Network Failure"))
                else -> _verifyOtpResult.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleVerifyOtpResponse(response: Response<VerifyOtpResponse>): Resource<VerifyOtpResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}
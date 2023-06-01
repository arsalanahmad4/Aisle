package com.example.aisle.data

import com.example.aisle.api.RetrofitInstance
import com.example.aisle.data.model.PhoneNumberApiRequest
import com.example.aisle.data.model.VerifyOtpRequest

class AisleRepository {

    suspend fun verifyPhoneNumber(phoneNumberApiRequest: PhoneNumberApiRequest) =
        RetrofitInstance.api.phoneNumberApi(phoneNumberApiRequest)

    suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest) =
        RetrofitInstance.api.verifyOtp(verifyOtpRequest)

    suspend fun getNotes(auth: String) =
        RetrofitInstance.api.getNotes(auth)
}
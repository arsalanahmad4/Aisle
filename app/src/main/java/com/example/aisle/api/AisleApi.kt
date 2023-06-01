package com.example.aisle.api

import com.example.aisle.data.model.PhoneNumberApiRequest
import com.example.aisle.data.model.PhoneNumberApiResponse
import com.example.aisle.data.model.VerifyOtpRequest
import com.example.aisle.data.model.VerifyOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AisleApi {

    @POST("users/phone_number_login")
    suspend fun phoneNumberApi(@Body loginRequest: PhoneNumberApiRequest): Response<PhoneNumberApiResponse>

    @POST("users/verify_otp")
    suspend fun verifyOtp(@Body verifyOtpRequest: VerifyOtpRequest): Response<VerifyOtpResponse>
}
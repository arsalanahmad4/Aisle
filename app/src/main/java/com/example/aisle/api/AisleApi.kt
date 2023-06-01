package com.example.aisle.api

import com.example.aisle.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface AisleApi {

    @POST("users/phone_number_login")
    suspend fun phoneNumberApi(@Body loginRequest: PhoneNumberApiRequest): Response<PhoneNumberApiResponse>

    @POST("users/verify_otp")
    suspend fun verifyOtp(@Body verifyOtpRequest: VerifyOtpRequest): Response<VerifyOtpResponse>

    @GET("users/test_profile_list")
    suspend fun getNotes(
        @Header("Authorization") auth: String,
    ): Response<NotesApiResponse>
}
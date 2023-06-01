package com.example.aisle.ui.userverification

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aisle.data.AisleRepository

class UserVerificationViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserVerificationViewModel::class.java)) {
            return UserVerificationViewModel(
                aisleRepository = AisleRepository(), application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
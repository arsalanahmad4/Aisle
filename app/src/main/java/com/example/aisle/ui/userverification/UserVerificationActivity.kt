package com.example.aisle.ui.userverification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.aisle.R

class UserVerificationActivity : AppCompatActivity() {

    lateinit var viewModel: UserVerificationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(
                this,
                UserVerificationViewModelFactory(application)
            )[UserVerificationViewModel::class.java]
        installSplashScreen()
        setContentView(R.layout.activity_user_verification)
    }
}
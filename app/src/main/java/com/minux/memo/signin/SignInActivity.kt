package com.minux.memo.signin

import android.os.Bundle
import com.minux.memo.BaseActivity
import com.minux.memo.databinding.ActivitySplashBinding

class SignInActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.splashAppNameTv.text = "TEST"
    }
}
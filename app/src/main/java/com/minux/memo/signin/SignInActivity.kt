package com.minux.memo.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.minux.memo.BaseActivity
import com.minux.memo.R
import com.minux.memo.databinding.ActivitySplashBinding
import java.util.*
import kotlin.concurrent.schedule

class SignInActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.splashAppNameTv.text = "TEST"
    }




}
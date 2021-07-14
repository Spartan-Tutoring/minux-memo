package com.minux.memo.splash

import android.content.Intent
import android.os.Bundle
import com.minux.memo.BaseActivity
import com.minux.memo.R
import com.minux.memo.databinding.ActivitySplashBinding
import com.minux.memo.main.MainActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        Timer().schedule(3000){
            startNextActivity(Intent(this@SplashActivity , MainActivity::class.java))

        }
    }

    private fun startNextActivity(intent: Intent){
        startActivity(intent)

        finish()
    }
}
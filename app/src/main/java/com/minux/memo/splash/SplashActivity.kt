package com.minux.memo.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.minux.memo.BaseActivity
import com.minux.memo.databinding.ActivitySplashBinding
import com.minux.memo.main.MainActivity
import com.minux.memo.signin.SignInActivity
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
            autoLogin()
        }
    }

    private fun autoLogin(){
        val spf : SharedPreferences = getSharedPreferences("memo", MODE_PRIVATE)
        val token = spf.getInt("token", 0)

        if(token != 0){
            startMainActivity()
            return
        }

        startSignInActivity()
    }

    private fun startSignInActivity(){
        val intent = Intent(this@SplashActivity , SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        startActivity(intent)
        finish()
    }
}
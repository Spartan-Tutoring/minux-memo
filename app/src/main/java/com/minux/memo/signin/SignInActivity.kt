package com.minux.memo.signin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Room
import com.minux.memo.BaseActivity
import com.minux.memo.data.db.UserDB
import com.minux.memo.databinding.ActivitySigninBinding
import com.minux.memo.main.MainActivity
import com.minux.memo.signup.SignUpActivity

class SignInActivity : BaseActivity() {
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signinSigninBtn.setOnClickListener(this)
        binding.signinSignupTv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v){
            binding.signinSigninBtn -> signIn()
            binding.signinSignupTv -> startSignUpActivity()
        }
    }

    private fun signIn(){
        val id = binding.signinIdEt.text.toString()
        val password = binding.signinPasswordEt.text.toString()

        if(id.isEmpty()){
            binding.signinIdEtLayout.error = "아이디를 올바르게 입력해주세요."
            return
        }

        if(password.isEmpty()){
            binding.signinIdEtLayout.error = "비밀번호를 올바르게 입력해주세요."
            return
        }

        val db: UserDB = Room.databaseBuilder(this, UserDB::class.java, "user-db").allowMainThreadQueries().build()
        val user = db.userDao().getUser(id, password)

        Log.d("user-db", "$user")

        user?.let{
            val spf : SharedPreferences = getSharedPreferences("memo", MODE_PRIVATE)
            val editor = spf.edit()

            editor.putInt("token", user.idx)
            editor.apply()

            startMainActivity()
        }
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        startActivity(intent)
        finish()
    }

    private fun startSignUpActivity(){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}
package com.minux.memo.signup

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Room
import com.minux.memo.BaseActivity
import com.minux.memo.data.db.UserDB
import com.minux.memo.databinding.ActivitySignupBinding
import com.minux.memo.data.entities.User

class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupBackIv.setOnClickListener(this)
        binding.signupSignupBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v){
            binding.signupBackIv -> finish()
            binding.signupSignupBtn -> signUp()
        }
    }

    private fun signUp(){
        val id = binding.signupIdEt.text.toString()
        val name = binding.signupNameEt.text.toString()
        val password = binding.signupPasswordEt.text.toString()
        val passwordCheck = binding.signupPasswordCheckEt.text.toString()
        binding.signupIdEtLayout.error = null
        binding.signupPasswordEtLayout.error = null
        binding.signupPasswordEtLayout.error = null
        binding.signupPasswordCheckEtLayout.error = null

        if(id.isEmpty()){
            binding.signupIdEtLayout.error = "아이디를 올바르게 입력해주세요."
            return
        }

        if(name.isEmpty()){
            binding.signupNameEtLayout.error = "이름을 올바르게 입력해주세요."
            return
        }

        if(password.isEmpty()){
            binding.signupPasswordEtLayout.error = "비밀번호를 올바르게 입력해주세요."
            return
        }

        if(password != passwordCheck){
            binding.signupPasswordCheckEtLayout.error = "비밀번호가 일치하지 않습니다."
            return
        }

        val db: UserDB = Room.databaseBuilder(this, UserDB::class.java, "user-db").allowMainThreadQueries().build()
        val alreadyUser = db.userDao().getUserById(id)

        if(alreadyUser === null){
            val user = User(id = id, pw = password, name = name)
            db.userDao().insertUser(user)

            val users = db.userDao().getUsers()
            for(_user in users){
                Log.d("user-db", "idx: ${_user.idx}, userId: ${_user.id}, userPw: ${_user.pw}, userName: ${_user.name}")
            }

            showDialog("가입에 성공했습니다.")
        }else{
            binding.signupIdEtLayout.error = "이미 존재하는 회원입니다."
        }
    }

    override fun onOKClicked() {
        super.onOKClicked()

        finish()
    }
}
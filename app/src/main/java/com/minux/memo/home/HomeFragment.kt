package com.minux.memo.home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.minux.memo.BaseActivity
import com.minux.memo.BaseFragment
import com.minux.memo.data.db.UserDB
import com.minux.memo.databinding.ActivityMainBinding
import com.minux.memo.databinding.FragmentHomeBinding
import com.minux.memo.signin.SignInActivity

class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        setUser()
        binding.homeNameTv.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v){
            binding.homeNameTv -> logout()
        }
    }

    private fun setUser(){
        val spf : SharedPreferences = requireActivity().getSharedPreferences("memo", MODE_PRIVATE)
        val token = spf.getInt("token", 0)

        if(token != 0){
            getUser(token)
        }
    }

    private fun getUser(idx: Int){
        val db: UserDB = Room.databaseBuilder(requireActivity(), UserDB::class.java, "user-db").allowMainThreadQueries().build()
        val user = db.userDao().getUserByIdx(idx)

        user?.let{
            binding.homeNameTv.text = it.name
        }
    }

    private fun logout(){
        val spf : SharedPreferences = requireActivity().getSharedPreferences("memo", MODE_PRIVATE)
        val editor = spf.edit()

        editor.remove("token")
        editor.apply()

        startSignInActivity()
    }

    private fun startSignInActivity(){
        val intent = Intent(requireActivity() , SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        startActivity(intent)
        requireActivity().finish()
    }
}
package com.minux.memo.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.minux.memo.BaseActivity
import com.minux.memo.R
import com.minux.memo.databinding.ActivityMainBinding
import com.minux.memo.home.HomeFragment
import com.minux.memo.memo.MemoFragment
import com.minux.memo.setting.SettingFragment
import com.minux.memo.signup.SignUpActivity
import com.minux.memo.write.WriteActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tabNames = listOf("Home", "Memo", "Setting")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tabLayout: TabLayout = findViewById(R.id.main_tabs)

        val pagerAdapter = MainPagerAdapter(this)
        pagerAdapter.addFragment(HomeFragment())
        pagerAdapter.addFragment(MemoFragment())
        pagerAdapter.addFragment(SettingFragment())

        val viewPager: ViewPager2 = findViewById(R.id.main_viewpager)

        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text = tabNames[position]
        }.attach()

        binding.mainWriteIv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v){
            binding.mainWriteIv -> startWriteActivity()
        }
    }

    private fun startWriteActivity(){
        val intent = Intent(this, WriteActivity::class.java)
        startActivity(intent)
    }
}
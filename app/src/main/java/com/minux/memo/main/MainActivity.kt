package com.minux.memo.main

import android.os.Bundle
import com.minux.memo.BaseActivity
import com.minux.memo.R

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
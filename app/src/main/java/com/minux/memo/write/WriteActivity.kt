package com.minux.memo.write

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Room
import com.google.gson.Gson
import com.minux.memo.BaseActivity
import com.minux.memo.data.db.MemoDB
import com.minux.memo.data.entities.Memo
import com.minux.memo.databinding.ActivityWriteBinding

class WriteActivity : BaseActivity() {
    private lateinit var binding: ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.writeBackIv.setOnClickListener(this)
        binding.writeDoneTv.setOnClickListener(this)

        if(intent.hasExtra("memo")){
            val gson = Gson()
            val json = intent.getStringExtra("memo")
            val memo = gson.fromJson(json, Memo::class.java)

            setMemo(memo)
        }
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v){
            binding.writeBackIv -> finish()
            binding.writeDoneTv -> write()
        }
    }

    private fun write(){
        val title = binding.writeTitleEt.text.toString()
        val content = binding.writeContentEt.text.toString()

        val spf : SharedPreferences = getSharedPreferences("memo", MODE_PRIVATE)
        val token = spf.getInt("token", 0)

        if(token != 0){
            val memo = Memo(title = title, content = content, writer = token)

            val db: MemoDB = Room.databaseBuilder(this, MemoDB::class.java, "memo-db").allowMainThreadQueries().build()
            db.memoDao().writeMemo(memo)

//            val memos = db.memoDao().getMemos()
//            for(_memo in memos){
//                Log.d("memo-db", "idx: ${_memo.idx}, title: ${_memo.title}, content: ${_memo.content}, bookmark: ${_memo.isBookmark}, writer: ${_memo.writer}")
//            }

            finish()
        }
    }

    private fun setMemo(memo: Memo){
        binding.writeTitleEt.setText(memo.title)
        binding.writeContentEt.setText(memo.content)
    }
}
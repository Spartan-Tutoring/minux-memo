package com.minux.memo.memo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.gson.Gson
import com.minux.memo.BaseFragment
import com.minux.memo.data.db.MemoDB
import com.minux.memo.data.entities.Memo
import com.minux.memo.databinding.FragmentMemoBinding
import com.minux.memo.write.WriteActivity
import java.text.FieldPosition

class MemoFragment : BaseFragment() {
    private lateinit var binding: FragmentMemoBinding
    private lateinit var rvAdapter: MemoRvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMemoBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        initRecyclerView()

        return view
    }

    override fun onStart() {
        super.onStart()

        val token = getWriterIdx();

        if (token != 0) {
            getMemos(token)
        }
    }

    private fun initRecyclerView() {
        val lm = LinearLayoutManager(requireActivity())
        binding.memoRecyclerview.layoutManager = lm

        rvAdapter = MemoRvAdapter(requireActivity())

        rvAdapter.setItemClickListener(object : MemoRvAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, memo: Memo) {
//                removeMemo(memo.idx, position)
                startWriteActivity(memo)
            }
        })

        binding.memoRecyclerview.adapter = rvAdapter
    }

    private fun getMemos(writer: Int) {
        val db: MemoDB = Room.databaseBuilder(requireActivity(), MemoDB::class.java, "memo-db").allowMainThreadQueries().build()
        val list = ArrayList(db.memoDao().getMemosByWriter(writer))

        for (_memo in list) {
            Log.d("memo-db", "idx: ${_memo.idx}, title: ${_memo.title}, content: ${_memo.content}, bookmark: ${_memo.isBookmark}, writer: ${_memo.writer}")
        }

        rvAdapter.addItems(list)
    }

    private fun getWriterIdx(): Int {
        val spf: SharedPreferences = requireActivity().getSharedPreferences("memo", AppCompatActivity.MODE_PRIVATE)
        return spf.getInt("token", 0)
    }

    private fun removeMemo(idx: Int, position: Int) {
        val db: MemoDB = Room.databaseBuilder(requireActivity(), MemoDB::class.java, "memo-db").allowMainThreadQueries().build()
        db.memoDao().removeMemo(idx)

        rvAdapter.removeItem(position)
    }

    private fun startWriteActivity(memo: Memo) {
        val intent = Intent(requireActivity(), WriteActivity::class.java)
        val gson = Gson()
        val json = gson.toJson(memo)

        intent.putExtra("memo", json)
        startActivity(intent)
    }
}
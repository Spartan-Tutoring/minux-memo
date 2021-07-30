package com.minux.memo.memo

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.minux.memo.R
import com.minux.memo.data.db.MemoDB
import com.minux.memo.data.entities.Memo

class MemoRvAdapter(private val context: Context): RecyclerView.Adapter<MemoRvAdapter.ViewHolder>(){
    val items = ArrayList<Memo>()
    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener{
        fun onClick(view: View, position: Int, memo: Memo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_memo, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position, items[position])
        }

        holder.ivBookmark.setOnClickListener{
            if(items[position].isBookmark == "N"){
                bookmarkMemo(items[position].idx, "Y")
                items[position].isBookmark = "Y"
                holder.ivBookmark.setImageDrawable(context.getDrawable(R.drawable.bookmark_active))
            }else if(items[position].isBookmark == "Y"){
                bookmarkMemo(items[position].idx, "N")
                items[position].isBookmark = "N"
                holder.ivBookmark.setImageDrawable(context.getDrawable(R.drawable.bookmark_inactive))
            }
        }
    }

    private fun bookmarkMemo(idx: Int, isBookmark: String){
        val db: MemoDB = Room.databaseBuilder(context, MemoDB::class.java, "memo-db").allowMainThreadQueries().build()
        db.memoDao().bookmarkMemo(idx, isBookmark)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(items: ArrayList<Memo>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        this.items.removeAt(position)
        notifyDataSetChanged()
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    inner class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!){
        val tvTitle: TextView = itemView?.findViewById(R.id.item_memo_title_tv)!!
        val tvContent: TextView = itemView?.findViewById(R.id.item_memo_content_tv)!!
        val tvDate: TextView = itemView?.findViewById(R.id.item_memo_date_tv)!!
        val ivBookmark: ImageView = itemView?.findViewById(R.id.item_memo_bookmark_iv)!!

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: Memo){

            tvTitle.text = item.title
            tvContent.text = item.content
            tvDate.text = item.datetime
            if(item.isBookmark == "Y"){
                ivBookmark.setImageDrawable(context.getDrawable(R.drawable.bookmark_active))
            }else{
                ivBookmark.setImageDrawable(context.getDrawable(R.drawable.bookmark_inactive))
            }
        }
    }
}
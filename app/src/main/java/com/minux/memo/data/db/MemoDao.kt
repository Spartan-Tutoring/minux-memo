package com.minux.memo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.minux.memo.data.entities.Memo

@Dao
interface MemoDao {
    @Query("SELECT * FROM MemoTable")
    fun getMemos(): List<Memo>

    @Query("SELECT * FROM MemoTable WHERE writer = :writer")
    fun getMemosByWriter(writer: Int): List<Memo>

    @Insert
    fun writeMemo(memo: Memo)

    @Query("DELETE FROM MemoTable WHERE idx = :id")
    fun removeMemo(id: Int)

    @Query("UPDATE MemoTable SET isBookmark = :isBookmark WHERE idx = :id")
    fun bookmarkMemo(id: Int, isBookmark: String)
}
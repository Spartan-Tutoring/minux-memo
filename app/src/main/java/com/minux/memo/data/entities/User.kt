package com.minux.memo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User (
        @PrimaryKey(autoGenerate = true) var idx: Int = 0,
    val id: String,
    val pw: String,
    val name: String,
)
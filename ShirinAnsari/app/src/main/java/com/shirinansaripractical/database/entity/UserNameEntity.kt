package com.shirinansaripractical.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserNameEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "userId") val userId: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "first") val first: String?,
    @ColumnInfo(name = "last") val last: String?
)
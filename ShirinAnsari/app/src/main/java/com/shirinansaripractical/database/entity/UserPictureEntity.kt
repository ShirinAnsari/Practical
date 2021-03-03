package com.shirinansaripractical.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserPictureEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "userId") val userId: String?,
    @ColumnInfo(name = "medium") val medium: String?,
    @ColumnInfo(name = "thumbnail") val thumbnail: String?
)
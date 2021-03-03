package com.shirinansaripractical.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.shirinansaripractical.database.entity.UserLocationEntity
import com.shirinansaripractical.database.entity.UserPictureEntity

@Dao
interface UserPictureDao {
    @Query("SELECT * FROM UserPictureEntity")
    fun getAll(): List<UserPictureEntity>

    @Insert
    fun insertAll(vararg users: UserPictureEntity)

    @Query("DELETE FROM UserPictureEntity")
    fun deleteAll()
}
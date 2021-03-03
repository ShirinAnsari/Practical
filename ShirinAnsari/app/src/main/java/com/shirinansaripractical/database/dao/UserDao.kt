package com.shirinansaripractical.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.shirinansaripractical.database.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<UserEntity>

    @Insert
    fun insertAll(vararg users: UserEntity)

    @Query("DELETE FROM UserEntity")
    fun deleteAll()
}
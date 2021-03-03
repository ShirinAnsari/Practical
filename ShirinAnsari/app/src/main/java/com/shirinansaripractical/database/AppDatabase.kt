package com.shirinansaripractical.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shirinansaripractical.database.dao.UserNameDao
import com.shirinansaripractical.database.dao.UserDao
import com.shirinansaripractical.database.dao.UserLocationDao
import com.shirinansaripractical.database.dao.UserPictureDao
import com.shirinansaripractical.database.entity.UserEntity
import com.shirinansaripractical.database.entity.UserLocationEntity
import com.shirinansaripractical.database.entity.UserNameEntity
import com.shirinansaripractical.database.entity.UserPictureEntity

@Database(
    entities = [UserEntity::class, UserNameEntity::class, UserLocationEntity::class, UserPictureEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao?
    abstract val userNameDao: UserNameDao?
    abstract val userLocationDao: UserLocationDao?
    abstract val userPictureDao: UserPictureDao?

    fun cleanUp() {
        appDB = null
    }

    companion object {
        private var appDB: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (null == appDB) {
                appDB =
                    buildDatabaseInstance(context)
            }
            return appDB
        }

        private fun buildDatabaseInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "user_db"
            )
                .allowMainThreadQueries().build()
        }
    }
}
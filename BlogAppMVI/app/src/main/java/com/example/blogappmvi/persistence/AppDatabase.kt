package com.example.blogappmvi.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.blogappmvi.model.AccountPoperties
import com.example.blogappmvi.model.AuthToken

@Database(entities = [AuthToken::class, AccountPoperties::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getAuthTokenDao(): AuthTokenDao

    abstract fun getAccountPropertiesDao(): AccountPropertiesDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }
}
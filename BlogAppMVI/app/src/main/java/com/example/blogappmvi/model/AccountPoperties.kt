package com.example.blogappmvi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "account_properties")
data class AccountPoperties(

    @Expose
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pk")
    var pk: Int,

    @Expose
    @ColumnInfo(name = "email")
    var email: String,

    @Expose
    @ColumnInfo(name = "username")
    var username: String
)
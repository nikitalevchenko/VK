package com.example.nikitalevcenko.vk.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.nikitalevcenko.vk.api.ApiFields
import com.example.nikitalevcenko.vk.db.DbFields
import com.example.nikitalevcenko.vk.db.Tables
import com.google.gson.annotations.SerializedName

@Entity(tableName = Tables.PROFILE)
data class Profile(

        @ColumnInfo(name = DbFields.ID)
        @PrimaryKey()
        @SerializedName(ApiFields.ID)
        var id: Long,

        @ColumnInfo(name = DbFields.FIRST_NAME)
        @SerializedName(ApiFields.FIRST_NAME)
        var firstName: String,

        @ColumnInfo(name = DbFields.LAST_NAME)
        @SerializedName(ApiFields.LAST_NAME)
        var lastName: String,

        @ColumnInfo(name = DbFields.SMALL_PHOTO)
        @SerializedName(ApiFields.PHOTO_100)
        var smallPhoto: String?
)
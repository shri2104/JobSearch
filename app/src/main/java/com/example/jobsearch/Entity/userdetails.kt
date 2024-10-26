package com.example.jobsearch.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name ="first_name") val firstName: String,
    @ColumnInfo(name ="last_name") val lastName: String,
    @ColumnInfo(name ="email") val email: String,
    @ColumnInfo(name ="password") val password: String,
    @ColumnInfo(name ="organization_name") val organizationName: String,
    @ColumnInfo(name ="address") val address: String
)

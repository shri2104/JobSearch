package com.example.jobsearch.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "jobs")
data class Job(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "organization_name") val organizationName: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "skills_required") val skillsRequired: List<String>,
    @ColumnInfo(name = "status") val status: String
)


class Converters {
    @TypeConverter
    fun fromSkillsList(skills: List<String>): String {
        return skills.joinToString(",")
    }

    @TypeConverter
    fun toSkillsList(skills: String): List<String> {
        return skills.split(",").map { it.trim() }
    }
}

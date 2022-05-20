package com.spravochnic.scbguide.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lectureTable")
data class LectureEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: Int = 0,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "type") var type: String?,
    @ColumnInfo(name = "image") var image: String?,
    @ColumnInfo(name = "description") var description: String?,
)
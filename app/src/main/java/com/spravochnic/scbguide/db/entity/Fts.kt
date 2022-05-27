package com.spravochnic.scbguide.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4(contentEntity = LectureEntity::class)
@Entity(tableName = "lectureTableFts")
data class LectureFtsEntity(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val rowid: Long,
    val name: String
)
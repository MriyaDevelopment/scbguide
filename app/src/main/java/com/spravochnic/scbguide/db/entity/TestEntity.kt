package com.spravochnic.scbguide.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "testTable")
data class TestEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: Int = 0,
    @ColumnInfo(name = "answer_type")
    var answer_type: String,
    @ColumnInfo(name = "type")
    var type: String,
    @ColumnInfo(name = "image")
    var image: String,
    @ColumnInfo(name = "questions")
    var questions: String,
    @ColumnInfo(name = "answer_one")
    var answer_one: String,
    @ColumnInfo(name = "answer_two")
    var answer_two: String,
    @ColumnInfo(name = "answer_three")
    var answer_three: String
)
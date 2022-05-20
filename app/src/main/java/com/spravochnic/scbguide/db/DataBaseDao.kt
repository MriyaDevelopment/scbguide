package com.spravochnic.scbguide.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spravochnic.scbguide.db.entity.LectureEntity

@Dao
interface DataBaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setLectureList(lectureList: List<LectureEntity>)

    @Query("SELECT * FROM lectureTable")
    suspend fun getLectureList(): List<LectureEntity>
}
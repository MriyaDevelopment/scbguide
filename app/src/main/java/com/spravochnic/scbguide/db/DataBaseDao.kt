package com.spravochnic.scbguide.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spravochnic.scbguide.db.entity.LectureCategoriesEntity
import com.spravochnic.scbguide.db.entity.LectureEntity
import com.spravochnic.scbguide.db.entity.TestEntity


@Dao
interface DataBaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setLectureList(lectureList: List<LectureEntity>)

    @Query("SELECT * FROM lectureTable")
    suspend fun getLectureList(): List<LectureEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setLectureCategoriesList(lectureCategoriesList: List<LectureCategoriesEntity>)

    @Query("SELECT * FROM lectureCategoriesTable")
    suspend fun getLectureCategoriesList(): List<LectureCategoriesEntity>

    @Query("SELECT snippet(lectureTableFts) FROM lectureTable JOIN lectureTableFts " + "ON lectureTable.id == lectureTableFts.rowid WHERE lectureTableFts.name " + "MATCH :search ORDER BY type")
    suspend fun filtered(search: String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setTestQuestionList(testList: List<TestEntity>)

    @Query("SELECT * FROM testTable")
    suspend fun getTestQuestionList(): List<TestEntity>
}
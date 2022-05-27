package com.spravochnic.scbguide.db

import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.RoomDatabase
import com.spravochnic.scbguide.db.entity.LectureCategoriesEntity
import com.spravochnic.scbguide.db.entity.LectureEntity
import com.spravochnic.scbguide.db.entity.LectureFtsEntity
import com.spravochnic.scbguide.db.entity.TestEntity

@Database(
    entities = [LectureEntity::class, LectureCategoriesEntity::class, LectureFtsEntity::class, TestEntity::class],
    version = 11
)
abstract class DataBase : RoomDatabase() {
    abstract fun dataBaseDao(): DataBaseDao
}
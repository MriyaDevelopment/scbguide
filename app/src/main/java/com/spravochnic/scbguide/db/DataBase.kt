package com.spravochnic.scbguide.db

import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.RoomDatabase
import com.spravochnic.scbguide.db.entity.LectureEntity

@Database(
    entities = [LectureEntity::class],
    version = 1
)
abstract class DataBase : RoomDatabase() {
    abstract fun dataBaseDao(): DataBaseDao
}
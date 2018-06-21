package com.github.teracy.roompagingsample.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.github.teracy.roompagingsample.data.db.dao.DietMemberDao
import com.github.teracy.roompagingsample.data.db.entity.DietMemberEntity

@Database(entities = [DietMemberEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dietMemberDao(): DietMemberDao
}
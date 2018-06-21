package com.github.teracy.roompagingsample.data.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * 議員データ
 */
@Entity(tableName = "diet_member")
data class DietMemberEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val name: String,
        val kana: String,
        val house: String,
        val party: String,
        // 選挙区
        val constituency: String?,
        // 比例区
        val block: String?
)
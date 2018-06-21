package com.github.teracy.roompagingsample.data.db.dao

import android.arch.persistence.room.*
import com.github.teracy.roompagingsample.data.db.entity.DietMemberEntity

@Dao
abstract class DietMemberDao {
    @Query("SELECT COUNT(*) FROM diet_member")
    abstract fun countMember(): Int

    @Query("SELECT * FROM diet_member ORDER BY kana LIMIT :limit OFFSET :offset")
    abstract fun getDietMembersLimitOffset(limit: Int, offset: Int): List<DietMemberEntity>

    @Query("SELECT * FROM diet_member WHERE name LIKE :name OR kana LIKE :name ORDER BY kana LIMIT :limit OFFSET :offset")
    abstract fun getDietMembersByNameLimitOffset(name: String, limit: Int, offset: Int): List<DietMemberEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(members: List<DietMemberEntity>)

    @Query("DELETE FROM diet_member")
    abstract fun deleteAll()

    @Transaction
    open fun clearAndInsert(members: List<DietMemberEntity>) {
        deleteAll()
        insertAll(members)
    }
}
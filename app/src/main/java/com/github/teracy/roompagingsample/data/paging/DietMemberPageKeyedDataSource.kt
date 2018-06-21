package com.github.teracy.roompagingsample.data.paging

import android.arch.paging.PageKeyedDataSource
import com.github.teracy.roompagingsample.data.db.AppDatabase
import com.github.teracy.roompagingsample.data.db.entity.DietMemberEntity

/**
 * Roomの議員Entity→表示用議員情報
 */
class DietMemberPageKeyedDataSource(private val database: AppDatabase, private val limit: Int, private val name: String?) : PageKeyedDataSource<Int, DietMember>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, DietMember>) {
        getMember(0) { dietMembers, next ->
            callback.onResult(dietMembers, null, next)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DietMember>) {
        getMember(params.key) { dietMembers, next ->
            callback.onResult(dietMembers, next)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DietMember>) {
        // 利用しない
    }

    private fun getMember(offset: Int, callback: (dietMembers: List<DietMember>, next: Int?) -> Unit) {
        val list = if (name == null || name.trim().isEmpty()) {
            database.dietMemberDao().getDietMembersLimitOffset(limit, offset)
        } else {
            database.dietMemberDao().getDietMembersByNameLimitOffset("$name%", limit, offset)
        }.map { it.convertToDietMember() }
        callback(list, offset + limit)
    }

    /**
     * 変換
     */
    private fun DietMemberEntity.convertToDietMember(): DietMember {
        val election =
                when (house) {
                    REPRESENTATIVES -> {
                        constituency?.let {
                            // 選挙区
                            house + it + "区"
                        } ?: block?.let {
                            // 比例区
                            house + it + "ブロック"
                        }
                    }
                    COUNCILORS -> {
                        constituency?.let {
                            // 選挙区
                            house + it + "選挙区"
                        } ?: block?.let {
                            // 比例区
                            house + it + "代表"
                        }
                    }
                    else -> ""
                } ?: ""

        return DietMember(id, name, kana, election, party)
    }
}
package com.github.teracy.roompagingsample.data.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.github.teracy.roompagingsample.data.db.AppDatabase
import com.github.teracy.roompagingsample.data.db.entity.DietMemberEntity

class DietMemberPageKeyedDataSource(private val database: AppDatabase, private val limit: Int, private val name: String?) : PageKeyedDataSource<Int, DietMember>() {
    // ローディング表示
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, DietMember>) {
        // 最初のページ取得
        getDietMember(0) { dietMembers, next ->
            callback.onResult(dietMembers, null, next)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DietMember>) {
        // 次のページ取得
        getDietMember(params.key) { dietMembers, next ->
            callback.onResult(dietMembers, next)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DietMember>) {
        // 前のページ取得：利用しない
    }

    private fun getDietMember(offset: Int, callback: (dietMembers: List<DietMember>, next: Int?) -> Unit) {
        loading.postValue(true)
        val list = if (name == null || name.trim().isEmpty()) {
            database.dietMemberDao().getDietMembersLimitOffset(limit, offset)
        } else {
            database.dietMemberDao().getDietMembersByNameLimitOffset("$name%", limit, offset)
        }.map { it.convert() }
        callback(list, offset + limit)
        loading.postValue(false)
    }

    /**
     * Roomの議員Entity→表示用議員情報に変換
     */
    private fun DietMemberEntity.convert(): DietMember {
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
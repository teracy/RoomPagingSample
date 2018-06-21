package com.github.teracy.roompagingsample.data.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.github.teracy.roompagingsample.data.db.AppDatabase
import com.github.teracy.roompagingsample.data.db.entity.DietMemberEntity
import com.github.teracy.roompagingsample.data.paging.COUNCILORS
import com.github.teracy.roompagingsample.data.paging.DietMember
import com.github.teracy.roompagingsample.data.paging.DietMemberPageKeyedDataSourceFactory
import com.github.teracy.roompagingsample.data.paging.REPRESENTATIVES
import com.github.teracy.roompagingsample.util.ioThread
import com.google.gson.Gson
import java.io.BufferedReader
import javax.inject.Inject

class MainRepository @Inject constructor(private val app: Application, private val gson: Gson, private val database: AppDatabase) {
    fun init() {
        ioThread {
            if (database.dietMemberDao().countMember() == 0) {
                // NOTE: サンプルなので更新チェックは省略
                // 衆議院議員データ
                val mhrList = gson.fromJson(app.assets.open("mhr.json").bufferedReader().use(BufferedReader::readText), MemberData::class.java)
                        .members.map { member ->
                    DietMemberEntity(
                            name = member.name,
                            kana = member.kana,
                            house = REPRESENTATIVES,
                            party = member.party,
                            constituency = member.constituency,
                            block = member.block
                    )
                }
                // 参議院議員データ
                val mhcList = gson.fromJson(app.assets.open("mhc.json").bufferedReader().use(BufferedReader::readText), MemberData::class.java)
                        .members.map { member ->
                    DietMemberEntity(
                            name = member.name,
                            kana = member.kana,
                            house = COUNCILORS,
                            party = member.party,
                            constituency = member.constituency,
                            block = member.block
                    )
                }
                val members = mutableListOf<DietMemberEntity>()
                        .apply {
                            addAll(mhrList)
                            addAll(mhcList)
                        }
                database.dietMemberDao().insertAll(members)
            }
        }
    }

    fun fetchDietMembers(name: String?): LiveData<PagedList<DietMember>> {
        val limit = 20
        val factory = DietMemberPageKeyedDataSourceFactory(database, limit, name)
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(limit)
                .setPageSize(limit)
                .build()
        return LivePagedListBuilder(factory, config).build()
    }
}
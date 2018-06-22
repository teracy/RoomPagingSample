package com.github.teracy.roompagingsample.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.github.teracy.roompagingsample.App
import com.github.teracy.roompagingsample.data.asset.MemberData
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

class MainViewModel @Inject constructor(
        application: Application,
        private val gson: Gson,
        private val database: AppDatabase) : AndroidViewModel(application) {

    // 議員リスト
    var dietMembers: LiveData<PagedList<DietMember>> = MutableLiveData()
    // ローディング表示
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    fun initialize() {
        ioThread {
            if (database.dietMemberDao().countMember() == 0) {
                // NOTE: サンプルなので更新チェックは省略
                val assetManager = getApplication<App>().assets
                // 衆議院議員データ
                val mhrList = gson.fromJson(assetManager.open("mhr.json").bufferedReader().use(BufferedReader::readText), MemberData::class.java)
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
                val mhcList = gson.fromJson(assetManager.open("mhc.json").bufferedReader().use(BufferedReader::readText), MemberData::class.java)
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

    fun fetchDietMembers(lifecycleOwner: LifecycleOwner, name: String?) {
        val limit = 20
        val factory = DietMemberPageKeyedDataSourceFactory(database, limit, name)
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(limit)
                .setPageSize(limit)
                .build()
        loading.removeObservers(lifecycleOwner)
        dietMembers.removeObservers(lifecycleOwner)
        loading = factory.source.loading
        dietMembers = LivePagedListBuilder(factory, config).build()
    }
}
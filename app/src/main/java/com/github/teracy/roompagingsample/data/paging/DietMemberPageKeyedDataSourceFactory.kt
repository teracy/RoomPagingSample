package com.github.teracy.roompagingsample.data.paging

import android.arch.paging.DataSource
import com.github.teracy.roompagingsample.data.db.AppDatabase

class DietMemberPageKeyedDataSourceFactory(
        database: AppDatabase,
        limit: Int,
        name: String?) : DataSource.Factory<Int, DietMember>() {
    val source = DietMemberPageKeyedDataSource(database, limit, name)

    override fun create(): DataSource<Int, DietMember> {
        return source
    }
}
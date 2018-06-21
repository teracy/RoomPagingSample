package com.github.teracy.roompagingsample.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import com.github.teracy.roompagingsample.data.paging.DietMember
import com.github.teracy.roompagingsample.data.repository.MainRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
        application: Application,
        private val repository: MainRepository) : AndroidViewModel(application) {

    var dietMembers: LiveData<PagedList<DietMember>> = MutableLiveData()

    fun init() {
        repository.init()

    }

    fun fetchDietMembers(lifecycleOwner: LifecycleOwner, name: String?) {
        dietMembers.removeObservers(lifecycleOwner)
        dietMembers = repository.fetchDietMembers(name)
    }
}
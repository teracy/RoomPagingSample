package com.github.teracy.roompagingsample.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import com.github.teracy.roompagingsample.R
import com.github.teracy.roompagingsample.data.paging.DietMember
import com.github.teracy.roompagingsample.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.init()

        // region 議員一覧
        val adapter = DietMemberAdapter(application, object : OnDietMemberClickListener {
            override fun onClick(member: DietMember?) {
                // TODO: 議員の直近国会の発言検索実装
            }
        })
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.recycler.adapter = adapter

        adapter.fetchDietMembers()
        // endreigon

        // region 検索条件入力
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // 特に何もしない
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 特に何もしない
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.fetchDietMembers(s?.toString()?.trim())
            }
        })
        // endregion
    }

    private fun DietMemberAdapter.fetchDietMembers(name: String? = null) {
        Timber.d("text:%s", name)
        viewModel.fetchDietMembers(this@MainActivity, name)
        viewModel.dietMembers.observe(this@MainActivity, Observer {
            Timber.d("dietMembers:%d", it?.size)
            submitList(it)
        })
    }
}

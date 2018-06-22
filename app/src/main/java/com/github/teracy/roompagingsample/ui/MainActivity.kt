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
import android.view.View
import com.github.teracy.roompagingsample.R
import com.github.teracy.roompagingsample.data.paging.DietMember
import com.github.teracy.roompagingsample.databinding.ActivityMainBinding
import com.github.teracy.roompagingsample.ui.speech.SpeechActivity
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
                if (member == null) {
                    return
                }
                val intent = SpeechActivity.createIntent(this@MainActivity, member.name)
                startActivity(intent)
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
                val text = s?.toString()?.trim() ?: ""
                adapter.fetchDietMembers(text)
                binding.ibClear.visibility = if (text.isNotBlank() && text.isNotEmpty()) View.VISIBLE else View.GONE
            }
        })
        binding.ibClear.setOnClickListener {
            binding.etSearch.setText("")
        }
        // endregion
    }

    private fun DietMemberAdapter.fetchDietMembers(name: String? = null) {
        Timber.d("text:%s", name)
        viewModel.fetchDietMembers(this@MainActivity, name)
        viewModel.loading.observe(this@MainActivity, Observer {
            binding.loading.visibility = if (it != false) View.VISIBLE else View.GONE
        })
        viewModel.dietMembers.observe(this@MainActivity, Observer {
            Timber.d("dietMembers:%d", it?.size)
            submitList(it)
        })
    }
}

package com.github.teracy.roompagingsample.ui.speech

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.teracy.roompagingsample.R
import com.github.teracy.roompagingsample.data.paging.Speech
import com.github.teracy.roompagingsample.databinding.ActivitySpeechBinding
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject

class SpeechActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SpeechViewModel
    private lateinit var binding: ActivitySpeechBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_speech)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        binding.appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val factor = (-verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
            binding.toolbarTextColorFactor = factor
        }

        val name = intent.extras.getString(ARG_NAME_OF_DIET_MEMBER)
        setTitle("${name}議員の発言一覧")
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SpeechViewModel::class.java)

        // region 発言一覧
        val adapter = SpeechAdapter(application, object : OnSpeechClickListener {
            override fun onClick(speech: Speech?) {
                // TODO: 余裕があれば
            }
        })
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.recycler.adapter = adapter
        adapter.fetchSpeech(name)
        // endregion
    }

    private fun SpeechAdapter.fetchSpeech(name: String) {
        Timber.d("name:%s", name)
        viewModel.fetchSpeech(this@SpeechActivity, name)
        viewModel.loading.observe(this@SpeechActivity, Observer {
            binding.loading.visibility = if (it != false) View.VISIBLE else View.GONE
        })
        viewModel.speeches.observe(this@SpeechActivity, Observer {
            Timber.d("speeches:%d", it?.size)
            submitList(it)
        })
        viewModel.message.observe(this@SpeechActivity, Observer {
            binding.tvNumberOfRecords.setText(it)
        })
    }

    companion object {
        private const val ARG_NAME_OF_DIET_MEMBER = "nameOfDietMember"
        fun createIntent(packageContext: Context, name: String): Intent {
            val intent = Intent(packageContext, SpeechActivity::class.java)
            intent.putExtra(ARG_NAME_OF_DIET_MEMBER, name)
            return intent
        }
    }
}
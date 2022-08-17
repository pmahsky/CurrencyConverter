package com.app.currency_converter.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.app.currency_converter.R
import com.app.currency_converter.databinding.ActivityMainBinding
import com.app.currency_converter.ui.adapter.CurrencyListAdapter
import com.app.currency_converter.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
@ActivityScoped
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        initViews()
        initObservers()
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private fun initViews() {
        initToolBar()
        initConversionListRecyclerView()
    }

    private fun initToolBar() {
        supportActionBar?.apply {
            title = getString(R.string.label_currency_conversion)
            setDisplayShowHomeEnabled(false)
            setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun initConversionListRecyclerView() {
//        binding.conversionsRecyclerView.apply {
//            setHasFixedSize(true)
//            layoutManager = LinearLayoutManager(context)
//            adapter = albumAdapter
//        }
    }

    private fun initObservers() {
        viewModel.stateMutableLiveData.observe(this) {
            Timber.i("stateMutableLiveData observer triggered with ${it.currencyList} ***")
            CurrencyListAdapter(
                this,
                layoutResource = android.R.layout.simple_spinner_dropdown_item,
                currencyList = it.currencyList
            ).apply {
                binding.currencySpinner.adapter = this
            }

            if (it.isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
            if (it.isError) {
                showSnackBar()
            }
        }
    }

    private fun showSnackBar() {
        Snackbar.make(
            binding.root,
            getString(R.string.error_message_error_occurred),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }
}
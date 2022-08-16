package com.app.currency_converter.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.app.currency_converter.R
import com.app.currency_converter.databinding.ActivityMainBinding
import com.app.currency_converter.domain.model.Result
import com.app.currency_converter.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    }

    private fun initToolBar() {
        supportActionBar?.apply {
            title = getString(R.string.label_currency_conversion)
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initObservers() {
        viewModel.dataLoadedLiveData.observe(this) {

        }
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch(Dispatchers.IO) {
            /**
             * Small delay so the user can actually see the splash screen
             * for a moment as feedback of an attempt to retrieve data.
             */
//            delay(250)
            when (val result = viewModel.fetchCurrencies()) {
                is Result.Success -> {
                    withContext(Dispatchers.Main) {
                        Timber.i("Data fetched successfully")
                    }
                }
                is Result.Error -> {
                    result.message?.let {
                        Timber.e(it)
                    }
                    withContext(Dispatchers.Main) {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.error_message_erro_occurred),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
package com.app.currency_converter.presentation.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.currency_converter.R
import com.app.currency_converter.databinding.ActivityMainBinding
import com.app.currency_converter.domain.model.Currency
import com.app.currency_converter.presentation.adapter.ConvertedCurrencyListAdapter
import com.app.currency_converter.presentation.adapter.CurrencyListAdapter
import com.app.currency_converter.util.Utils.hideKeyboard
import com.app.currency_converter.presentation.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import timber.log.Timber


@AndroidEntryPoint
@ActivityScoped
class MainActivity : AppCompatActivity() {
    private lateinit var convertedCurrencyListAdapter: ConvertedCurrencyListAdapter
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
        initSpinnerItemSelectedListener()
        initRecyclerView()
        initKeyboardDoneActionListener()
//        initTextWatcher()
    }

    private fun initToolBar() {
        supportActionBar?.apply {
            title = getString(R.string.label_currency_conversion)
            setDisplayShowHomeEnabled(false)
            setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun initSpinnerItemSelectedListener() {
        binding.currencySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                initCurrencyConversion()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }
    }

    private fun initRecyclerView() {
        convertedCurrencyListAdapter = ConvertedCurrencyListAdapter()
        binding.conversionsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = convertedCurrencyListAdapter
        }
    }

    private fun initKeyboardDoneActionListener() {
        binding.enterAmountEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                initCurrencyConversion()
                hideKeyboard()
                true
            } else false
        }
    }

    private fun initTextWatcher() {
        binding.enterAmountEditText.addTextChangedListener { object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                initCurrencyConversion()
            }

            override fun afterTextChanged(p0: Editable?) {
                TODO("Not yet implemented")
            }

        } }
    }

    private fun initCurrencyConversion(){
        if (viewModel.processInputAmount(binding.enterAmountEditText.text.toString())) {
            viewModel.convertEnteredCurrencyToSupportedCurrencies(
                currency = binding.currencySpinner.selectedItem as Currency,
                enteredAmountTobeConverted = binding.enterAmountEditText.text.toString()
            )
        } else {
            Snackbar.make(
                binding.root,
                getString(R.string.error_message_invalid_amount),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun initObservers() {
        viewModel.stateMutableLiveData.observe(this) {
            Timber.i("stateMutableLiveData observer triggered with shouldUpdateConvertedCurrencyList:${it.shouldUpdateConvertedCurrencyList} and data: ${it.currencyList} ***")
            if (it.shouldUpdateConvertedCurrencyList) {
                convertedCurrencyListAdapter.setConvertedCurrencyList(it.currencyList)
            } else {
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
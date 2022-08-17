package com.app.currency_converter.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.currency_converter.R
import com.app.currency_converter.databinding.ListItemConvertedCurrencyBinding
import com.app.currency_converter.domain.model.Currency
import java.util.ArrayList

internal class ConvertedCurrencyListAdapter() :
    RecyclerView.Adapter<ConvertedCurrencyListAdapter.ViewHolder>() {
    private val convertedCurrencyList = ArrayList<Currency>()

    fun setConvertedCurrencyList(currencyList: List<Currency>) {
        convertedCurrencyList.clear()
        convertedCurrencyList.addAll(currencyList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(getLayout(viewGroup))
    }

    private fun getLayout(viewGroup: ViewGroup): ListItemConvertedCurrencyBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.list_item_converted_currency,
            viewGroup,
            false
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = convertedCurrencyList[position]
        viewHolder.bind(item)
    }

    override fun getItemCount(): Int {
        return convertedCurrencyList.size
    }

    class ViewHolder(private val binding: ListItemConvertedCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal fun bind(currency: Currency) {
            binding.currency = currency
            binding.convertedConvertedValueTextView.text = currency.getExchangeRate()//TODO there is an error coming when setting this through xml, related to Cannot find a setter for <android.widget.TextView android:text> that accepts parameter type 'double'
            binding.executePendingBindings()
        }
    }
}

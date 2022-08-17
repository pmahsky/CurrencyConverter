package com.app.currency_converter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.app.currency_converter.domain.model.Currency

internal class CurrencyListAdapter(
    context: Context,
    @LayoutRes private val layoutResource: Int,
    private var currencyList: List<Currency>
) : ArrayAdapter<Currency>(context, layoutResource, currencyList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createViewFromResource(position, convertView, parent)
    }

    private fun createViewFromResource(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view: TextView = convertView as TextView?
            ?: LayoutInflater.from(context).inflate(layoutResource, parent, false) as TextView
        view.text = currencyList[position].currencyCode
        return view
    }
}
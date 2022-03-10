package com.kristina.onlineshopapp.ui.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kristina.onlineshopapp.currency.CurrencyParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyConverterViewModel : ViewModel() {

    private val currencyParser = CurrencyParser()

    private val _currencyData = MutableLiveData<HashMap<String, Pair<Double, Double>>>()

    val currencyData: LiveData<HashMap<String, Pair<Double, Double>>>
        get() = _currencyData

//    init {
//        updateExchangeRate()
//    }

    fun updateExchangeRate() {
        viewModelScope.launch {
            withContext(Dispatchers.Default){
                currencyParser.update()
            }
            _currencyData.value = currencyParser.data
        }


    }
}
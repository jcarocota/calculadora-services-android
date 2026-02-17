package com.ebc.calculadora_services.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebc.calculadora_services.network.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class TipViewModel: ViewModel() {
    private val _billAmount = MutableStateFlow("")
    val billAmount: StateFlow<String> = _billAmount

    private val _tipPercent = MutableStateFlow<Int?>(null)
    val tipPercent: StateFlow<Int?> = _tipPercent

    private val _customTipAmount = MutableStateFlow("")
    val customTipAmount: StateFlow<String> = _customTipAmount

    private val _loadingRandomTip = MutableStateFlow(false)
    val loadingRandomTip: StateFlow<Boolean> = _loadingRandomTip



    val totalToPay: StateFlow<String> = combine(
        _billAmount,
        _tipPercent,
        _customTipAmount
    ) {
        billAmountString, tipPercentInt, customTipAmountString ->

        val customTipAmountDouble  = customTipAmountString.toDoubleOrNull()
        val billAmount = billAmountString.toDoubleOrNull()

        val tip:Double = if (customTipAmountString.isNotBlank() &&
            customTipAmountDouble != null &&
            customTipAmountDouble > 0) {
            customTipAmountDouble
        } else {

            (billAmount?:0.0) * ((tipPercentInt?: 0) / 100.0)
        }

        val tipFormatted = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
        tipFormatted.format((billAmount?:0.0) + tip)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )


    fun setBillAmount(value: String) {
        _billAmount.value = value
    }

    fun setTipPercent(value: Int?) {
        _tipPercent.value = value
        _customTipAmount.value = ""
    }

    fun setCustomTipAmount(value: String) {
        _customTipAmount.value = value
        _tipPercent.value = null
    }

    fun reset() {
        _billAmount.value = ""
        _tipPercent.value = null
        _customTipAmount.value = ""
    }

    fun getRandomTip() {
        _loadingRandomTip.value = true

        val billAmount  = _billAmount.value.toDoubleOrNull()

        if(billAmount != null && billAmount > 0) {
            viewModelScope.launch {
                try {
                    val response = Network.propinasApi.propinaRandom(billAmount)

                    if(response.isSuccessful) {
                        val body = response.body()?.trim()
                        val tip = body?.toDoubleOrNull()
                        if(tip != null) {
                            setCustomTipAmount(tip.toString())
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        _loadingRandomTip.value = false
    }

}
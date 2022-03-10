package com.kristina.onlineshopapp.ui.currency

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kristina.onlineshopapp.databinding.FragmentCurrencyConverterBinding

class CurrencyConverterFragment : Fragment() {

    lateinit var binding: FragmentCurrencyConverterBinding
    lateinit var viewModel: CurrencyConverterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCurrencyConverterBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(this).get(CurrencyConverterViewModel::class.java)

        binding.update.setOnClickListener {
            binding.loading.visibility = View.VISIBLE

            viewModel.updateExchangeRate()

            binding.loading.visibility = View.GONE
        }

        viewModel.currencyData.observe(viewLifecycleOwner) { currencyData ->
            if(currencyData.size != 0) {
                val usd = currencyData["usd"]
                binding.usdBuy.text = usd?.first.toString()
                binding.usdSell.text = usd?.second.toString()

                val eur = currencyData["eur"]
                binding.eurBuy.text = eur?.first.toString()
                binding.eurSell.text = eur?.second.toString()

                val rub = currencyData["rub"]
                binding.rubBuy.text = rub?.first.toString()
                binding.rubSell.text = rub?.second.toString()
            }
        }

        viewModel.updateExchangeRate()

        return binding.root
    }

}
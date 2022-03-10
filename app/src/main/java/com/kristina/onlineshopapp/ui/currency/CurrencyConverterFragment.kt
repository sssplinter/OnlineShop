package com.kristina.onlineshopapp.ui.currency

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.kristina.onlineshopapp.databinding.FragmentCurrencyConverterBinding
import com.kristina.onlineshopapp.utils.CONNECTION_ERROR
import com.kristina.onlineshopapp.utils.isOnline

class CurrencyConverterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentCurrencyConverterBinding.inflate(layoutInflater, container, false)

        val viewModel = ViewModelProvider(this).get(CurrencyConverterViewModel::class.java)

        if (!isOnline(requireContext())) {
            Toast.makeText(context, CONNECTION_ERROR, Toast.LENGTH_LONG).show()
        }

        binding.update.setOnClickListener {
            binding.loading.visibility = View.VISIBLE

            viewModel.updateExchangeRate()

            binding.loading.visibility = View.GONE
        }

        viewModel.currencyData.observe(viewLifecycleOwner) { currencyData ->
            if (currencyData.size != 0) {
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
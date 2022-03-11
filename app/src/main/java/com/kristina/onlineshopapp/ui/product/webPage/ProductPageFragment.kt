package com.kristina.onlineshopapp.ui.product.webPage

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.kristina.onlineshopapp.databinding.FragmentProductPageBinding

class ProductPageFragment : Fragment() {

    lateinit var binding: FragmentProductPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductPageBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.webView) {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl("https://fakestoreapi.com/")
        }
    }
}


package com.kristina.onlineshopapp.ui.settings

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.kristina.onlineshopapp.R
import com.kristina.onlineshopapp.utils.APP_PREFS
import com.kristina.onlineshopapp.utils.MODE_KEY

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.change_mode).setOnClickListener {
            val currentNightMode =
                view.context.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)

            requireContext().getSharedPreferences(APP_PREFS, 0).edit().apply {
                putBoolean(MODE_KEY, currentNightMode == Configuration.UI_MODE_NIGHT_NO)
                apply()
            }

            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}
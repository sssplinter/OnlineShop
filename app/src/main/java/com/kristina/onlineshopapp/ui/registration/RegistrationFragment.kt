package com.kristina.onlineshopapp.ui.registration

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation.findNavController
import com.kristina.onlineshopapp.R

const val MODE_KEY = "NightMode"
const val FIRST_NAME_KEY = "FirstName"
const val LAST_NAME_KEY = "LastName"
const val EMAIL_KEY = "Email"
const val AVATAR_KEY = "Avatar"
const val APP_PREFS = "AppSettingPrefs"

class RegistrationFragment : Fragment() {

    // todo databinding
    lateinit var appSettingPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(false)

        appSettingPrefs = context?.getSharedPreferences(APP_PREFS, 0)!!

        val currentNightMode =
            requireContext().resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)

        val isNightMode = appSettingPrefs.getBoolean(MODE_KEY, false)

//        if (isNightMode && currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        } else if (!isNightMode && currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }

        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstName = view.findViewById<EditText>(R.id.first_name)
        val lastName = view.findViewById<EditText>(R.id.last_name)
        val email = view.findViewById<EditText>(R.id.email)


        view.findViewById<Button>(R.id.register_button).setOnClickListener {

            if (firstName.text.isNotEmpty() && lastName.text.isNotEmpty() && email.text.isNotEmpty()) {

                requireContext().getSharedPreferences(APP_PREFS, 0).edit().apply {
                    putString(FIRST_NAME_KEY, firstName.text.toString())
                    putString(LAST_NAME_KEY, lastName.text.toString())
                    putString(EMAIL_KEY, email.text.toString())
                    apply()
                }

                findNavController(it).navigate(
                    R.id.action_registrationFragment_to_profileFragment
                )
            } else {
                Toast.makeText(
                    context,
                    "All fields must be filled in to register.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        view.findViewById<Button>(R.id.mode_button).setOnClickListener {
            val currentNightMode =
                it.context.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)

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


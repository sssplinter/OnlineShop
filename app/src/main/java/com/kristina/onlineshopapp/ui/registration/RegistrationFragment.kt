package com.kristina.onlineshopapp.ui.registration

import android.content.SharedPreferences
import android.os.Bundle
import android.os.SharedMemory
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.kristina.onlineshopapp.R
import kotlin.properties.Delegates

const val MODE_KEY = "NightMode"

class RegistrationFragment : Fragment() {

    // todo databinding
    lateinit var appSettingPrefs: SharedPreferences
    lateinit var sharedPrefsEdit: SharedPreferences.Editor
    var isNightMode by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(false)

        appSettingPrefs = context?.getSharedPreferences("AppSettingPrefs", 0)!!
        sharedPrefsEdit = appSettingPrefs.edit()


        sharedPrefsEdit.putBoolean(MODE_KEY, false)
        sharedPrefsEdit.apply()

        isNightMode = appSettingPrefs.getBoolean(MODE_KEY, false)

        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }

        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstName = view.findViewById<EditText>(R.id.first_name)
        val lastName = view.findViewById<EditText>(R.id.last_name)
        val email = view.findViewById<EditText>(R.id.email)

        view.findViewById<Button>(R.id.register_button).setOnClickListener {

            //todo uncomment
//            if (firstName.text.isNotEmpty() && lastName.text.isNotEmpty() && email.text.isNotEmpty()) {
//                findNavController(it).navigate(
//                    R.id.action_registrationFragment_to_profileFragment
//                )
//            } else {
//                Toast.makeText(
//                    context,
//                    "All fields must be filled in to register.",
//                    Toast.LENGTH_LONG
//                ).show()
//            }

            findNavController(it).navigate(
                R.id.action_registrationFragment_to_productListFragment
            )
        }

        view.findViewById<Button>(R.id.mode_button).setOnClickListener {
            if (isNightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefsEdit.putBoolean(MODE_KEY, false)
                sharedPrefsEdit.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefsEdit.putBoolean(MODE_KEY, true)
                sharedPrefsEdit.apply()
            }
        }
    }

}
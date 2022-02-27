package com.kristina.onlineshopapp.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.kristina.onlineshopapp.R

class RegistrationFragment : Fragment() {

    // todo databinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstName = view.findViewById<EditText>(R.id.first_name)
        val lastName = view.findViewById<EditText>(R.id.last_name)
        val email = view.findViewById<EditText>(R.id.email)

        view.findViewById<Button>(R.id.register_button).setOnClickListener {

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
                R.id.action_registrationFragment_to_profileFragment
            )
        }
    }

}
package com.kristina.onlineshopapp.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kristina.onlineshopapp.R
import com.kristina.onlineshopapp.databinding.FragmentProfileBinding
import android.widget.Toast
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

import java.util.*
import com.google.android.gms.location.LocationResult

class ProfileFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    private lateinit var map: GoogleMap
    lateinit var client: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    private val locationRequestCode = 1000
    private var wayLatitude = 0.0
    private var wayLongitude: Double = 0.0

    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding.viewModel = viewModel

//        viewModel.avatarUri.observe(viewLifecycleOwner) { uri ->
//            if (uri != null){
//                binding.avatarImg.setImageURI(uri)
//            }
//        }

        binding.map.onCreate(savedInstanceState)
        binding.map.onResume()
        binding.map.getMapAsync(this)

        client = LocationServices.getFusedLocationProviderClient(requireContext())

        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 20 * 1000

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                locationRequestCode
            )
        } else {
            client.lastLocation.addOnSuccessListener(requireActivity()) { location ->
                if (location != null) {
                    wayLatitude = location.latitude
                    wayLongitude = location.longitude
                    Log.i(
                        "Location",
                        java.lang.String.format(
                            Locale.US,
                            "%s -- %s",
                            wayLatitude,
                            wayLongitude
                        )
                    )
                }
            }
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (location != null) {
                        wayLatitude = location.latitude
                        wayLongitude = location.longitude
                        Log.i(
                            "Callback",
                            java.lang.String.format(
                                Locale.US,
                                "%s -- %s",
                                wayLatitude,
                                wayLongitude
                            )
                        )
                    }
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.avatarImg.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            if (imageUri != null) {
                viewModel.setAvatarUri(imageUri!!)
                binding.avatarImg.setImageURI(imageUri)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            map = googleMap
            val bsuir = LatLng(53.912150, 27.595061)
            map.addMarker(MarkerOptions().position(bsuir).title("Current location"))
            map.moveCamera(CameraUpdateFactory.newLatLng(bsuir))
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    client.lastLocation.addOnSuccessListener(requireActivity()) { location ->
                        if (location != null) {
                            wayLatitude = location.getLatitude()
                            wayLongitude = location.getLongitude()
                            Log.i(
                                "Loc",
                                java.lang.String.format(
                                    Locale.US,
                                    "%s -- %s",
                                    wayLatitude,
                                    wayLongitude
                                )
                            )
                        }
                    }
                } else {
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
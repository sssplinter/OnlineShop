package com.kristina.onlineshopapp.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kristina.onlineshopapp.databinding.FragmentProfileBinding
import android.widget.Toast
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

import com.google.android.gms.location.LocationResult
import com.kristina.onlineshopapp.utils.*

class ProfileFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    private lateinit var map: GoogleMap
    lateinit var client: FusedLocationProviderClient

    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding.viewModel = viewModel

        binding.map.onCreate(savedInstanceState)
        binding.map.onResume()
        binding.map.getMapAsync(this)

        client = LocationServices.getFusedLocationProviderClient(requireContext())

        getLastLocation()

        return binding.root
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {

                client.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        val currentLocation = LatLng(location.latitude, location.longitude)
                        map.addMarker(MarkerOptions().position(currentLocation).title(CURRENT_LOC))
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10f))
                    }
                }
            } else {
                Toast.makeText(context, TURN_ON_LOCATION, Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_ID
            )
        }
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            val location = LatLng(mLastLocation.latitude, mLastLocation.longitude)
            map.addMarker(MarkerOptions().position(location).title(CURRENT_LOC))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        client = LocationServices.getFusedLocationProviderClient(requireActivity())
        client.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            avatarImg.setOnClickListener {
                val gallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(gallery, pickImage)
            }

            saveUserInfo.setOnClickListener {
                if (firstName.text.isNotEmpty() && lastName.text.isNotEmpty() && email.text.isNotEmpty()) {
                    requireContext().getSharedPreferences(APP_PREFS, 0).edit().apply {
                        putString(FIRST_NAME_KEY, firstName.text.toString())
                        putString(LAST_NAME_KEY, lastName.text.toString())
                        putString(EMAIL_KEY, email.text.toString())
                        apply()
                    }
                } else {
                    Toast.makeText(context, REGISTRATION_WARNING, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            if (imageUri != null) {
                viewModel.setAvatarUri(imageUri!!)
                binding.avatarImg.setImageURI(imageUri)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            map = googleMap
        }
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

}
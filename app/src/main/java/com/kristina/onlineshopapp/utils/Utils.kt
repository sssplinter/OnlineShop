package com.kristina.onlineshopapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log

const val CONNECTION_WARNING = "There is no internet connection. Data may be outdated."
const val PRODUCT = "product"
const val OPEN_SOURCE_WARNING = "There is no internet connection. Unable to open source of product."
const val REGISTRATION_WARNING = "All fields must be filled in to register."
const val CURRENT_LOC = "Current location"
const val PERMISSION_ID = 42
const val TURN_ON_LOCATION = "Turn on location"
const val MODE_KEY = "NightMode"
const val FIRST_NAME_KEY = "FirstName"
const val LAST_NAME_KEY = "LastName"
const val EMAIL_KEY = "Email"
const val AVATAR_KEY = "Avatar"
const val APP_PREFS = "AppSettingPrefs"

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
    }
    return false
}
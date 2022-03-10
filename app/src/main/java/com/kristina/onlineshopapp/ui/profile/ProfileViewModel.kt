package com.kristina.onlineshopapp.ui.profile

import android.app.Application
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kristina.onlineshopapp.ui.registration.AVATAR_KEY
import com.kristina.onlineshopapp.ui.registration.EMAIL_KEY
import com.kristina.onlineshopapp.ui.registration.FIRST_NAME_KEY
import com.kristina.onlineshopapp.ui.registration.LAST_NAME_KEY

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    var appSettingPrefs: SharedPreferences =
        application.getSharedPreferences("AppSettingPrefs", 0)!!

    private val _firstName = MutableLiveData<String>()
    val firstName: LiveData<String>
        get() = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String>
        get() = _lastName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _avatarUri = MutableLiveData<Uri>()
    val avatarUri: LiveData<Uri>
        get() = _avatarUri

    init {
        _firstName.value ="First name   ${appSettingPrefs.getString(FIRST_NAME_KEY, "")}"
        _lastName.value = "Last name    ${appSettingPrefs.getString(LAST_NAME_KEY, "")}"
        _email.value =    "Email        ${appSettingPrefs.getString(EMAIL_KEY, "")}"
        // TODO
        _avatarUri.value = appSettingPrefs.getString(AVATAR_KEY, "")?.toUri()
        Log.i("Avatar", _avatarUri.value.toString())
    }

    fun setAvatarUri(uri: Uri) {
        _avatarUri.value = uri
        appSettingPrefs.edit().putString(AVATAR_KEY, uri.toString()).apply()
    }

}
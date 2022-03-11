package com.kristina.onlineshopapp.ui.profile

import android.app.Application
import android.content.SharedPreferences
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kristina.onlineshopapp.utils.APP_PREFS
import com.kristina.onlineshopapp.utils.*

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private var appSettingPrefs: SharedPreferences =
        application.getSharedPreferences(APP_PREFS, 0)!!

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
        _firstName.value = appSettingPrefs.getString(FIRST_NAME_KEY, "")
        _lastName.value = appSettingPrefs.getString(LAST_NAME_KEY, "")
        _email.value = appSettingPrefs.getString(EMAIL_KEY, "")
        _avatarUri.value = appSettingPrefs.getString(AVATAR_KEY, "")?.toUri()
    }

    fun setAvatarUri(uri: Uri) {
        _avatarUri.value = uri
        appSettingPrefs.edit().putString(AVATAR_KEY, uri.toString()).apply()
    }

}
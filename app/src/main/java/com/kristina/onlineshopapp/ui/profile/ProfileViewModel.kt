package com.kristina.onlineshopapp.ui.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

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


}
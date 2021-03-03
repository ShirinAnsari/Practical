package com.shirinansaripractical.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    var showMsg = MutableLiveData<String>()

}
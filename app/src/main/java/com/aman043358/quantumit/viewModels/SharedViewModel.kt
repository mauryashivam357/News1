package com.aman043358.quantumit.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _onTabChangeListener = MutableLiveData<TAB>()

    val onTabChangeListener: LiveData<TAB>
        get() = _onTabChangeListener

    fun onTabChanged(tab: TAB) {
        _onTabChangeListener.value = tab
    }

    enum class TAB(val position: Int) {
        LOGIN (0),
        SIGNUP (1)
    }
}


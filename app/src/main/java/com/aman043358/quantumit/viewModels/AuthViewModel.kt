package com.aman043358.quantumit.viewModels

import androidx.lifecycle.ViewModel
import com.aman043358.quantumit.repositories.AuthRepository

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()
    val authListener = repository.getTaskListener

    fun login(username: String, password: String) {
        repository.login(username, password)
    }

    fun register(username: String, password: String) {
        repository.register(username, password)
    }
}
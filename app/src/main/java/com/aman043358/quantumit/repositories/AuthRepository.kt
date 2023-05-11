package com.aman043358.quantumit.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val taskMutableLiveData = MutableLiveData<Task<AuthResult>>()

    val getTaskListener: LiveData<Task<AuthResult>>
        get() = taskMutableLiveData

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            taskMutableLiveData.value = task
        }
    }

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            taskMutableLiveData.value = task
        }
    }

}
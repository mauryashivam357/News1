package com.aman043358.quantumit.utils

import com.google.firebase.auth.FirebaseAuth


object AuthUtils {
    fun isUserLoggedIn(): Boolean{
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        return currentUser != null
    }

    fun getUUID(): String{
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        return currentUser!!.uid
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}
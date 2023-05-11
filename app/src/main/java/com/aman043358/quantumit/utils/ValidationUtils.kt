package com.aman043358.quantumit.utils

import android.widget.Toast
import com.aman043358.quantumit.databinding.FragmentLoginBinding
import com.aman043358.quantumit.databinding.FragmentSignUpBinding

object ValidationUtils {
    fun signUpPageValidation(binding: FragmentSignUpBinding): Boolean {
        if (binding.etName.text.isEmpty()) {
            Toast.makeText(binding.root.context, "Please enter valid name", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etEmail.text.isEmpty()) {
            Toast.makeText(binding.root.context, "Please enter valid email", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etNumber.text.isEmpty()) {
            Toast.makeText(binding.root.context, "Please enter valid number", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etPassword.text.isEmpty()) {
            Toast.makeText(binding.root.context, "Please enter valid password", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!binding.checkBox.isChecked){
            Toast.makeText(binding.root.context, "Please agree to our Terms & Condition", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun loginPageValidation(binding: FragmentLoginBinding): Boolean {
        if (binding.etEmail.text.isEmpty()) {
            Toast.makeText(binding.root.context, "Please enter valid email", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etPassword.text.isEmpty()) {
            Toast.makeText(binding.root.context, "Please enter valid password8", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
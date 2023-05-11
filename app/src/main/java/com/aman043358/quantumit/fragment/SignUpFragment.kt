package com.aman043358.quantumit.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.aman043358.quantumit.activities.HomeActivity
import com.aman043358.quantumit.databinding.FragmentSignUpBinding
import com.aman043358.quantumit.utils.AuthUtils
import com.aman043358.quantumit.utils.ValidationUtils
import com.aman043358.quantumit.viewModels.AuthViewModel
import com.aman043358.quantumit.viewModels.SharedViewModel
import com.aman043358.quantumit.viewModels.SharedViewModel.TAB

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSignIn.setOnClickListener {
            sharedViewModel.onTabChanged(TAB.LOGIN)
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (ValidationUtils.signUpPageValidation(binding)) {
                binding.progressIndicator.visibility = View.VISIBLE
                register(email, password)
            }
        }
    }

    private fun register(email: String, password: String) {
        authViewModel.register(email, password)
        authViewModel.authListener.observe(viewLifecycleOwner) { task ->
            if (task.isSuccessful) {
                startActivity(Intent(requireActivity(), HomeActivity::class.java))
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
            binding.progressIndicator.visibility = View.GONE
        }
    }
}
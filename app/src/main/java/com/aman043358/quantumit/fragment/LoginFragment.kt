package com.aman043358.quantumit.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.aman043358.quantumit.R
import com.aman043358.quantumit.activities.HomeActivity
import com.aman043358.quantumit.databinding.FragmentLoginBinding
import com.aman043358.quantumit.utils.ValidationUtils
import com.aman043358.quantumit.viewModels.AuthViewModel
import com.aman043358.quantumit.viewModels.SharedViewModel
import com.aman043358.quantumit.viewModels.SharedViewModel.TAB
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        return binding.root
    }

    private fun signInGoogle() {
        launcher.launch(googleSignInClient.signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }


    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {task ->
            navigateToHome(task)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvRegisterNow.setOnClickListener {
            sharedViewModel.onTabChanged(TAB.SIGNUP)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (ValidationUtils.loginPageValidation(binding)) {
                binding.progressIndicator.visibility = View.VISIBLE
                login(email, password)
            }
        }

        binding.btnGoogle.setOnClickListener {
            signInGoogle()
        }
    }

    private fun login(email: String, password: String) {
        authViewModel.login(email, password)
        authViewModel.authListener.observe(viewLifecycleOwner) { task ->
            navigateToHome(task)
        }
    }

    private fun navigateToHome(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            requireActivity().finish()
        } else {
            Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
        binding.progressIndicator.visibility = View.GONE
    }
}
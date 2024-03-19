package com.skripsi.sawitku.presentation.auth.login

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.fragment.findNavController
import com.skripsi.sawitku.R
import com.skripsi.sawitku.databinding.FragmentLoginBinding
import com.skripsi.sawitku.presentation.MainActivity
import java.net.URLEncoder

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val PREFS_NAME = "LoginPrefs"
    private val PREF_EMAIL = "email"
    private val PREF_REMEMBER_ME = "rememberMe"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null && user.isEmailVerified) {
                            Toast.makeText(requireContext(), "Login Berhasil", Toast.LENGTH_SHORT).show()
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(requireContext(), "Email belum diverifikasi", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Email dan Password Tidak Cocok", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Email & Sandi Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show()
            }
            if (binding.cbRememberMe.isChecked) {
                saveCredentials(email)
            } else {
                clearCredentials()
            }
        }

        binding.tvRegisterBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        val prefs: SharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, 0)
        val email: String? = prefs.getString(PREF_EMAIL, null)
        val rememberMe: Boolean = prefs.getBoolean(PREF_REMEMBER_ME, false)

        binding.etEmail.setText(email)
        binding.cbRememberMe.isChecked = rememberMe
        binding.tvForgotPasswordBtn.setOnClickListener {
            sendWhatsAppMessage()
        }
    }

    private fun saveCredentials(email: String) {
        val prefs: SharedPreferences.Editor = requireActivity().getSharedPreferences(PREFS_NAME, 0).edit()
        prefs.putString(PREF_EMAIL, email)
        prefs.putBoolean(PREF_REMEMBER_ME, true)
        prefs.apply()
    }

    private fun clearCredentials() {
        val prefs: SharedPreferences.Editor = requireActivity().getSharedPreferences(PREFS_NAME, 0).edit()
        prefs.remove(PREF_EMAIL)
        prefs.putBoolean(PREF_REMEMBER_ME, false)
        prefs.apply()
    }

    private fun sendWhatsAppMessage() {
        val phoneNumber = "6282122909276"
        val message = "Saya lupa password akun saya!\nEmail:--\nNama:--\n\nTrimakasih"
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=${URLEncoder.encode(message, "UTF-8")}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
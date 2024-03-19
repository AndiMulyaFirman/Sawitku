package com.skripsi.sawitku.presentation.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.skripsi.sawitku.R
import com.skripsi.sawitku.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val rootView = binding.root

        binding.apply {
            btnRegister.setOnClickListener {
                registerUser()
            }
            tvLoginBtn.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
        return rootView
    }

    private fun registerUser() {
        val nama = binding.etNama.text.toString().trim()
        val nomor_wa = binding.etNomorWa.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (nama.isEmpty()) {
            binding.tilNama.error = "Nama tidak boleh kosong"
            return
        } else {
            binding.tilNama.error = null
        }
        if (email.isEmpty()) {
            binding.tilEmail.error = "Email tidak boleh kosong"
            return
        } else {
            binding.tilEmail.error = null
        }
        if (password.isEmpty()) {
            binding.tilPassword.error = "Password tidak boleh kosong"
            return
        } else {
            binding.tilPassword.error = null
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    sendEmailVerification()
                    saveUserInfoToFirestore(nama, nomor_wa, email)
                    findNavController().navigate(R.id.action_registerFragment_to_verifikasiFragment)
                } else {
                    Toast.makeText(requireContext(), "Gagal mendaftar: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun sendEmailVerification() {
        val user = firebaseAuth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Email verifikasi telah dikirim", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Gagal mengirim email verifikasi", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserInfoToFirestore(nama: String, nomor_wa: String, email: String) {
        val user = hashMapOf(
            "nama" to nama,
            "nohp" to nomor_wa,
            "email" to email
        )
        val usersCollection = db.collection("users")
        user?.let {
            usersCollection.document(firebaseAuth.currentUser?.uid ?: "")
                .set(it)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Gagal menyimpan informasi pengguna", Toast.LENGTH_SHORT).show()
                }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
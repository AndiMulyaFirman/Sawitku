package com.skripsi.sawitku.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skripsi.sawitku.databinding.ActivityMainBinding
import com.skripsi.sawitku.presentation.auth.AuthActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tambahkan listener ke tombol logout
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        // Lakukan proses logout di sini
        FirebaseAuth.getInstance().signOut()

        // Setelah logout, arahkan kembali ke halaman login
        navigateToLoginActivity()
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        // Membersihkan tumpukan aktivitas sehingga tidak bisa kembali ke MainActivity setelah logout
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

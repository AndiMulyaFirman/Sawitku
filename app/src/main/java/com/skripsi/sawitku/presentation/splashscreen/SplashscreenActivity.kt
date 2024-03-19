package com.skripsi.sawitku.presentation.splashscreen

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.skripsi.sawitku.databinding.ActivitySplashscreenBinding
import com.skripsi.sawitku.presentation.auth.AuthActivity
import com.skripsi.sawitku.presentation.main.MainActivity

class SplashscreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        playAnimation()

        // Menunda navigasi ke MainActivity atau LoginFragment
        Handler().postDelayed({
            navigateBasedOnLoginStatus()
        }, SPLASH_SCREEN_DURATION) // SPLASH_SCREEN_DURATION adalah durasi tampilan splash screen dalam milidetik
    }

    private fun navigateBasedOnLoginStatus() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // Jika pengguna sudah login, langsung arahkan ke MainActivity
            navigateToMainActivity()
        } else {
            // Jika pengguna belum login, arahkan ke AuthActivity (halaman login)
            navigateToLoginFragment()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLoginFragment() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun playAnimation() {
        val alphaAnimator = ObjectAnimator.ofFloat(binding.icSplashscreen, "alpha", 0.0f, 1.0f)
        alphaAnimator.duration = 1000
        alphaAnimator.start()
    }

    companion object {
        private const val SPLASH_SCREEN_DURATION = 2500L // Ubah durasi tampilan splash screen sesuai kebutuhan Anda
    }
}

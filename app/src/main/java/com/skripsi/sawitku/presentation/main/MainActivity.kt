package com.skripsi.sawitku.presentation.main

import com.skripsi.sawitku.R
import com.skripsi.sawitku.presentation.home.HomeFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.skripsi.sawitku.presentation.pesanan.PesananFragment
import com.skripsi.sawitku.presentation.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private var doubleBackToExitPressedOnce = false
    private val TOAST_DURATION = 2000

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tekan lagi untuk keluar", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, TOAST_DURATION.toLong())
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = true
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.miHome -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.miPesanan -> {
                    replaceFragment(PesananFragment())
                    true
                }
                R.id.miProfile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
        bottomNavigationView.selectedItemId = R.id.miHome
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
    companion object {
        const val EXTRA_FRAGMENT = "extra_fragment"
        const val FRAGMENT_HOME = "fragment_home"
    }
}
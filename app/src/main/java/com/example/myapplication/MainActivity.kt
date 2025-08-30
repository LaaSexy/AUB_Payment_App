package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent
import com.example.myapplication.auth.LoginActivity
import com.example.myapplication.ui.DashboardFragement
import com.example.myapplication.ui.HistoryFragment
import com.example.myapplication.ui.PaymentsFragment
import com.example.myapplication.ui.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!isUserLoggedIn()) {
            redirectToLogin()
            return
        }
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DashboardFragement())
                .commit()
        }
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    selectedFragment = DashboardFragement()
                }
                R.id.navigation_payments -> {
                    selectedFragment = PaymentsFragment()
                }
                R.id.navigation_history -> {
                    selectedFragment = HistoryFragment()
                }
                R.id.navigation_profile -> {
                    selectedFragment = ProfileFragment()
                }
            }
            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
            }
            true
        }
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        return sharedPreferences.getBoolean("is_logged_in", false)
    }

    private fun redirectToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun logout() {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", false)
        editor.apply()
        redirectToLogin()
    }
}
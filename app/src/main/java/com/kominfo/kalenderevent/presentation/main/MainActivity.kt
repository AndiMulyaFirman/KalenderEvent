package com.kominfo.kalenderevent.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kominfo.kalenderevent.R
import com.kominfo.kalenderevent.presentation.home.HomeFragment
import com.kominfo.kalenderevent.presentation.profil.ProfilFragment
import com.kominfo.kalenderevent.presentation.seacrh.SearchFragment
import com.kominfo.kalenderevent.presentation.tambahEvent.TambahEventFragment
import com.kominfo.kalenderevent.presentation.tiketSaya.TiketSayaFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private var doubleBackToExitPressedOnce = false
    private val TOAST_DURATION = 2000 // Durasi toast dalam milidetik (misalnya 2000ms = 2 detik)

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tekan lagi untuk keluar", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, TOAST_DURATION.toLong())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.miHome -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.miTiketSaya -> {
                    replaceFragment(TiketSayaFragment())
                    true
                }
                R.id.miProfile -> {
                    replaceFragment(ProfilFragment())
                    true
                }


                else -> false
            }
        }


        // Set the default selected menu item
        bottomNavigationView.selectedItemId = R.id.miHome
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}

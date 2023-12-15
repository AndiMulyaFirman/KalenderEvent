package com.kominfo.kalenderevent.presentation.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.kominfo.kalenderevent.R
import com.kominfo.kalenderevent.presentation.auth.login.LoginFragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Cek apakah savedInstanceState adalah null untuk mencegah fragment ditambahkan
        // lebih dari sekali saat konfigurasi berubah (seperti rotasi layar).
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                // Gunakan animasi yang telah dibuat untuk transisi fragment.
                setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.slide_out
                )
                // Ganti FrameLayout dengan id fragment_container dengan LoginFragment.
                replace(R.id.fragment_container, LoginFragment())
            }
        }
    }
}

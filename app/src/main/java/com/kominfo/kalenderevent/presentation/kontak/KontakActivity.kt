package com.kominfo.kalenderevent.presentation.kontak

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.kominfo.kalenderevent.R

class KontakActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kontak)

        val btnBack: ImageButton = findViewById(R.id.btnBack)
        val btnHubungi: Button = findViewById(R.id.button)

        // Mengatur fungsi onClickListener untuk tombol kembali (btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        // Mengatur fungsi onClickListener untuk tombol "Hubungi"
        btnHubungi.setOnClickListener {
            showContactOptions()
        }
    }

    private fun showContactOptions() {
        val phoneNumber = "62882007121779"
        val email = "eventjogja@gmail.com"
        val message = "Halo admin, saya butuh bantuan!"

        // Membuat Intent untuk membuka aplikasi WhatsApp
        val whatsappIntent = Intent(Intent.ACTION_VIEW)
        whatsappIntent.data = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=$message")

        // Membuat Intent untuk membuka aplikasi email
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:$email")
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bantuan")
        emailIntent.putExtra(Intent.EXTRA_TEXT, message)

        // Menampilkan dialog pilihan untuk membuka WhatsApp atau aplikasi email
        val chooser = Intent.createChooser(emailIntent, "Pilih aplikasi")
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(whatsappIntent))
        startActivity(chooser)
    }
}

package com.kominfo.kalenderevent.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.kominfo.kalenderevent.R
import com.kominfo.kalenderevent.presentation.kalender.KalenderActivity
import com.kominfo.kalenderevent.presentation.pendaftaran.Verif1Fragment
import com.kominfo.kalenderevent.response.ResponseItem
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var tvNamaEvent: TextView
    private lateinit var recDate: TextView
    private lateinit var recTime: TextView
    private lateinit var recMaps1: TextView
    private lateinit var recMaps2: TextView
    private lateinit var tvDeskripsiEvent: TextView
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val maps = intent.getStringArrayListExtra(EXTRA_MAPS)
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        val btnCalendar: ImageButton = findViewById(R.id.btCalender)
        val btnMaps: ImageButton = findViewById(R.id.btMaps)
        imageView = findViewById(R.id.imageView)
        tvNamaEvent = findViewById(R.id.tvNamaEvent)
        recDate = findViewById(R.id.recDate)
        recTime = findViewById(R.id.recTime)
        recMaps1 = findViewById(R.id.recMaps_1)
        recMaps2 = findViewById(R.id.recMaps_2)
        tvDeskripsiEvent = findViewById(R.id.tvDeskripsiEvent)

        val btnDaftarSekarang: Button = findViewById(R.id.button)

        firestore = FirebaseFirestore.getInstance()

        // Mendapatkan data ResponseItem dan deskripsi dari Intent
        val responseItem = intent.getParcelableExtra<ResponseItem>(EXTRA_RESPONSE_ITEM)
        val deskripsi = intent.getStringExtra(EXTRA_DESCRIPTION) ?: ""
        responseItem?.let {
            // Menampilkan data menggunakan metode displayData
            displayData(it, deskripsi)
        } ?: run {
            Toast.makeText(this, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
            finish()
        }
        btnBack.setOnClickListener {
            finish()
        }
        btnCalendar.setOnClickListener {
            startActivity(Intent(this@DetailActivity, KalenderActivity::class.java))
        }

        // Memberikan action pada tombol "Maps"
        btnMaps.setOnClickListener {
            // Mendapatkan geolocation dari Firestore (gantilah dengan cara yang sesuai dengan struktur data Anda)
            val latitude = responseItem?.maps?.getOrNull(0)?.toDoubleOrNull() ?: 0.0
            val longitude = responseItem?.maps?.getOrNull(1)?.toDoubleOrNull() ?: 0.0

            // Membuat URI untuk membuka aplikasi Google Maps dengan koordinat lokasi
            val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")

            // Membuat Intent untuk membuka aplikasi Google Maps
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            // Memeriksa apakah aplikasi Google Maps terpasang
            if (mapIntent.resolveActivity(packageManager) != null) {
                // Menjalankan Intent
                startActivity(mapIntent)
            } else {
                // Jika aplikasi Google Maps tidak terpasang, berikan pesan kepada pengguna
                Toast.makeText(
                    this,
                    "Aplikasi Google Maps tidak terpasang. Silakan instal untuk menggunakan fitur ini.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Memberikan action pada tombol "Daftar Sekarang"
        btnDaftarSekarang.setOnClickListener {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, VerifLoginFragment())
            fragmentTransaction.addToBackStack(null) // Optional: Add the transaction to the back stack
            fragmentTransaction.commit()
        }


        val fabShare: FloatingActionButton = findViewById(R.id.fabShare)
        fabShare.setOnClickListener {
            responseItem?.let {
                shareData(it)
            }
        }
    }

    private fun displayData(responseItem: ResponseItem, deskripsi: String?) {
        // Tampilkan gambar menggunakan Picasso
        Picasso.get().load(responseItem.url).into(imageView)

        // Setel teks nama event
        tvNamaEvent.text = responseItem.nama

        // Format tanggal dan waktu
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        // Tampilkan tanggal dan waktu
        val dateText = dateFormat.format(responseItem.calender?.toDate())
        val timeText = timeFormat.format(responseItem.calender?.toDate())
        recDate.text = dateText
        recTime.text = timeText

        // Tampilkan lokasi dalam bentuk daftar
        responseItem.lokasi?.let {
            recMaps1.text = it.getOrNull(0) ?: ""
            recMaps2.text = it.getOrNull(1) ?: ""
        }

        // Tampilkan deskripsi yang diterima dari Intent atau Firestore
        tvDeskripsiEvent.text = deskripsi ?: "No Description"
    }

    private fun shareData(responseItem: ResponseItem) {
        val websiteURL = "https://bit.ly/TasteMatchApps"
        val shareText = "Coba resep makanan ini: ${responseItem.nama}\nLihat resep lengkap di: $websiteURL"
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
        startActivity(Intent.createChooser(shareIntent, "Bagikan via"))
    }

    companion object {
        const val EXTRA_RESPONSE_ITEM = "extra_response_item"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_MAPS = "extra_maps"
    }

}

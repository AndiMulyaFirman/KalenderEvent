package com.kominfo.kalenderevent.presentation.auth.login

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.kominfo.kalenderevent.R
import androidx.navigation.fragment.findNavController
import com.kominfo.kalenderevent.databinding.FragmentLoginBinding
import com.kominfo.kalenderevent.presentation.pendaftaran.DaftarEventActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    // Nama file preferensi bersama
    private val PREFS_NAME = "LoginPrefs"

    // Kunci untuk menyimpan email dan status "Ingat Saya" dalam preferensi bersama
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

        // Menetapkan OnClickListener pada tombol login
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Login Berhasil", Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireContext(), DaftarEventActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(requireContext(), "Email dan Pasword Tidak Cocok", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Email & Sandi Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show()

            }

            // Simpan email dan status "Ingat Saya" jika dicentang
            if (binding.cbRememberMe.isChecked) {
                saveCredentials(email)
            } else {
                clearCredentials()
            }
        }

        // Menetapkan OnClickListener pada teks "Daftar sekarang!"
        binding.tvRegisterBtn.setOnClickListener {
            // Navigasi ke RegisterFragment
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        // Mengecek apakah ada email yang disimpan dan status "Ingat Saya"
        val prefs: SharedPreferences =
            requireActivity().getSharedPreferences(PREFS_NAME, 0)
        val email: String? = prefs.getString(PREF_EMAIL, null)
        val rememberMe: Boolean = prefs.getBoolean(PREF_REMEMBER_ME, false)

        // Menetapkan email ke TextInputEditText
        binding.etEmail.setText(email)

        // Menetapkan status "Ingat Saya" ke CheckBox
        binding.cbRememberMe.isChecked = rememberMe

        // Menetapkan OnClickListener pada teks "Lupa Kata Sandi"
        binding.tvForgotPasswordBtn.setOnClickListener {
            sendWhatsAppMessage()
        }
    }

    private fun saveCredentials(email: String) {
        val prefs: SharedPreferences.Editor =
            requireActivity().getSharedPreferences(PREFS_NAME, 0).edit()
        prefs.putString(PREF_EMAIL, email)
        prefs.putBoolean(PREF_REMEMBER_ME, true)
        prefs.apply()
    }

    private fun clearCredentials() {
        val prefs: SharedPreferences.Editor =
            requireActivity().getSharedPreferences(PREFS_NAME, 0).edit()
        prefs.remove(PREF_EMAIL)
        prefs.putBoolean(PREF_REMEMBER_ME, false)
        prefs.apply()
    }

    private fun sendWhatsAppMessage() {
        // Implementasi aksi untuk mengirim pesan WhatsApp
        // Misalnya, membuka aplikasi WhatsApp dengan nomor tujuan tertentu
        val phoneNumber = "6281234567890" // Ganti dengan nomor tujuan Anda
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber")
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

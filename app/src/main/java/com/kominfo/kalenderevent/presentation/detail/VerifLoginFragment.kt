package com.kominfo.kalenderevent.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kominfo.kalenderevent.databinding.FragmentVerifLoginBinding
import com.kominfo.kalenderevent.presentation.auth.AuthActivity

class VerifLoginFragment : Fragment() {

    private lateinit var binding: FragmentVerifLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referensi view menggunakan View Binding
        binding.closeButton.setOnClickListener {
            // Aksi yang akan diambil saat closeButton diklik
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }

        binding.yesButton.setOnClickListener {
            val intent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(intent)
        }

        binding.noButton.setOnClickListener {
            // Aksi yang akan diambil saat noButton diklik
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }
    }
}
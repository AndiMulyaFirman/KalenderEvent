package com.kominfo.kalenderevent.presentation.splashscreen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.kominfo.kalenderevent.databinding.ActivitySplashscreenBinding
import com.kominfo.kalenderevent.presentation.main.MainActivity

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

        // Atur nilai awal alpha menjadi 0
        binding.tvNameApp.alpha = 0f
        binding.tvDescApp.alpha = 0f

        playAnimation()

        // Handler untuk berpindah ke MainActivity setelah animasi selesai
        binding.root.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)
    }

    private fun playAnimation() {
        val title1 = ObjectAnimator.ofFloat(binding.tvNameApp, View.ALPHA, 1f).setDuration(1000)
        val title2 = ObjectAnimator.ofFloat(binding.tvDescApp, View.ALPHA, 1f).setDuration(1500)

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(title1, title2)
        animatorSet.start()
    }
}

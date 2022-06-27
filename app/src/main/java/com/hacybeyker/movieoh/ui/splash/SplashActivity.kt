package com.hacybeyker.movieoh.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.hacybeyker.movieoh.databinding.ActivitySplashBinding
import com.hacybeyker.movieoh.ui.home.HomeActivity
import kotlinx.coroutines.Runnable

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    companion object {
        const val TIMER_SPLASH = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            },
            TIMER_SPLASH
        )
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

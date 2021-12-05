package com.jcy.dessertorderapp.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jcy.dessertorderapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    companion object { private const val SPLASH_DELAY_TIME = 1500L }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        moveMain()
    }

    private fun moveMain(){
        CoroutineScope(
            Dispatchers.Default).launch {
            delay(SPLASH_DELAY_TIME)
            CoroutineScope(Dispatchers.Main).launch {
                Intent(this@SplashActivity, MainActivity::class.java).run {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(this)
                }
                finish()
            }
            }
    }

}
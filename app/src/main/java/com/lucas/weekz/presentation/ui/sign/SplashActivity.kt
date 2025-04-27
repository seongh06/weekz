package com.lucas.weekz.presentation.ui.sign

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import com.lucas.weekz.presentation.theme.ThemedApp
import com.lucas.weekz.presentation.ui.main.MainActivity
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            ThemedApp{
                LaunchedEffect(key1 = true) {
                    delay(3000L)
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                SplashScreen()
            }
        }
    }
}
package com.lucas.weekz.presentation.ui.sign

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.lucas.weekz.presentation.theme.AppTheme
import com.lucas.weekz.presentation.theme.LocalAppTheme
import com.lucas.weekz.presentation.theme.ThemedApp
import com.lucas.weekz.presentation.ui.main.KEY_SELECTED_THEME
import com.lucas.weekz.presentation.ui.main.MainActivity
import com.lucas.weekz.presentation.ui.main.PREFS_NAME
import com.lucas.weekz.presentation.ui.main.Screen
import com.lucas.weekz.presentation.ui.main.toAppTheme

const val EXTRA_DESTINATION_ROUTE = "destination_route" // 상수가 이렇게 정의되어 있어야 합니다.

// AppTheme enum을 String으로 변환 및 복원하는 헬퍼 함수
fun AppTheme.toName(): String = this.name
fun String.toAppTheme(): AppTheme? = try { AppTheme.valueOf(this) } catch (e: IllegalArgumentException) { null }

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val sharedPreferences =
                remember { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

            val systemIsDark = isSystemInDarkTheme()
            val defaultSystemTheme = if (systemIsDark) AppTheme.DARK1 else AppTheme.LIGHT1

            var currentAppTheme by remember {
                mutableStateOf(
                    sharedPreferences.getString(KEY_SELECTED_THEME, null)?.toAppTheme()
                        ?: defaultSystemTheme
                )
            }
            
            CompositionLocalProvider(LocalAppTheme provides currentAppTheme) {
                ThemedApp(currentAppTheme = currentAppTheme) {
                    val context = LocalContext.current

                    SplashScreen(
                        onBoxClick = {
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                            finish()
                        },
                        onSettingsClick = {
                            val intent = Intent(context, MainActivity::class.java).apply {
                                putExtra(EXTRA_DESTINATION_ROUTE, Screen.SettingThemeScreen.route)
                            }
                            context.startActivity(intent)
                            finish()
                        }
                    )
                }
            }
        }
    }
}
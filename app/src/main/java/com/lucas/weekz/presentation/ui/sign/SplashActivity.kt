package com.lucas.weekz.presentation.ui.sign

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.lucas.weekz.presentation.theme.ThemedApp
import com.lucas.weekz.presentation.ui.main.MainActivity
import com.lucas.weekz.presentation.ui.main.Screen

const val EXTRA_DESTINATION_ROUTE = "destination_route" // 상수가 이렇게 정의되어 있어야 합니다.

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            ThemedApp {
                val context = LocalContext.current // Composable 내에서 Context 가져오기

                // Splash Screen UI 표시 및 Box 클릭 시 실행될 람다 함수 전달
                SplashScreen(
                    onBoxClick = {
                        // Box 클릭 시 MainActivity로 이동
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        finish() // 현재 Activity 종료
                    },
                    onSettingsClick = {
                        // 설정 아이콘 클릭 시 MainActivity로 이동하며 SettingThemeScreen으로 바로 가도록 정보 전달
                        val intent = Intent(context, MainActivity::class.java).apply {
                            // 이동할 목적지 화면 경로를 Intent에 추가
                            putExtra(EXTRA_DESTINATION_ROUTE, Screen.SettingThemeScreen.route)
                        }
                        context.startActivity(intent)
                        finish() // 현재 Activity 종료
                    }
                )
            }
        }
    }
}
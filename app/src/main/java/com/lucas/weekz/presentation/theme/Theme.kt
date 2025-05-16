package com.lucas.weekz.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.core.view.WindowCompat
import com.lucas.weekz.R

enum class AppTheme {
    DARK1, DARK2, DARK3, DARK4, DARK5,
    LIGHT1, LIGHT2, LIGHT3, LIGHT4
}

data class ThemeBackground(
    val theme: AppTheme,
    val backgroundDrawableId: Int
)

val themeBackgroundMap: Map<AppTheme, Int> = mapOf(
    AppTheme.DARK1 to R.drawable.bg_dark_1,
    AppTheme.DARK2 to R.drawable.bg_dark_2,
    AppTheme.DARK3 to R.drawable.bg_dark_3,
    AppTheme.DARK4 to R.drawable.bg_dark_4,
    AppTheme.DARK5 to R.drawable.bg_dark_5,
    AppTheme.LIGHT1 to R.drawable.bg_light_1,
    AppTheme.LIGHT2 to R.drawable.bg_light_2,
    AppTheme.LIGHT3 to R.drawable.bg_light_3,
    AppTheme.LIGHT4 to R.drawable.bg_light_4
)



val DarkColorScheme = darkColorScheme(
    primary = White,
    surface = Black
)
val LightColorScheme = lightColorScheme(
    primary = Black,
    surface = White
)

fun getThemeColorScheme(theme: AppTheme) = when(theme){
    AppTheme.DARK1, AppTheme.DARK2, AppTheme.DARK3, AppTheme.DARK4, AppTheme.DARK5 -> DarkColorScheme
    AppTheme.LIGHT1, AppTheme.LIGHT2, AppTheme.LIGHT3, AppTheme.LIGHT4 -> LightColorScheme
}

val LocalAppTheme = compositionLocalOf { AppTheme.LIGHT1 } // 기본 테마를 DARK1로 설정

@Composable
fun ThemedApp(
    // MainActivity에서 관리하는 currentAppTheme을 직접 받도록 수정
    currentAppTheme: AppTheme, // MainActivity로부터 현재 테마를 전달받음
    content: @Composable () -> Unit
) {
    val isSystemInDarkTheme = isSystemInDarkTheme()
    val finalTheme = currentAppTheme
    val currentBackgroundId = themeBackgroundMap[finalTheme] ?:
    if (isSystemInDarkTheme) R.drawable.bg_dark_1 else R.drawable.bg_light_1 // fallback 배경

    val colorScheme = getThemeColorScheme(finalTheme)

    WeekzTheme( // MaterialTheme을 직접 호출하거나, WeekzTheme 내부 로직을 여기에 통합할 수 있습니다.
        darkTheme = finalTheme in listOf(
            AppTheme.DARK1,
            AppTheme.DARK2,
            AppTheme.DARK3,
            AppTheme.DARK4,
            AppTheme.DARK5
        ),
        dynamicColor = false, // 필요에 따라 true로 설정하여 동적 색상 사용
        colorSchemeToUse = colorScheme // WeekzTheme에 결정된 colorScheme 전달
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
            // colorScheme을 Surface에 직접 적용할 수도 있지만,
            // MaterialTheme(colorScheme = ...)을 통해 전체적으로 적용하는 것이 일반적입니다.
        ) {
            Image(
                painter = painterResource(id = currentBackgroundId),
                contentDescription = "배경 이미지",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            content()
        }
    }
}

@Composable
fun WeekzTheme(
    darkTheme: Boolean, // ThemedApp에서 계산된 darkTheme 여부를 전달받음
    dynamicColor: Boolean = true,
    colorSchemeToUse: androidx.compose.material3.ColorScheme, // ThemedApp에서 결정된 ColorScheme을 전달받음
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> colorSchemeToUse // ThemedApp에서 전달받은 colorScheme 사용
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb() // 필요에 따라 상태 표시줄 색상 조정
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme // 상태 표시줄 아이콘 색상 조정
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Typography 정의가 있다면 추가
        content = content
    )
}
package com.lucas.weekz.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.foundation.Image
import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.lucas.weekz.R
import java.time.format.TextStyle

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
fun ThemedApp(content: @Composable () -> Unit) {
    val isSystemInDarkTheme = isSystemInDarkTheme()
    val defaultTheme = if (isSystemInDarkTheme) AppTheme.DARK1 else AppTheme.LIGHT1
    val currentTheme = LocalAppTheme.current
    val finalTheme = when{
        currentTheme in listOf(AppTheme.LIGHT1,AppTheme.LIGHT2,AppTheme.LIGHT3, AppTheme.LIGHT4) && isSystemInDarkTheme == false -> currentTheme
        currentTheme in listOf(AppTheme.DARK1,AppTheme.DARK2,AppTheme.DARK3,AppTheme.DARK4,AppTheme.DARK5) && isSystemInDarkTheme -> currentTheme
        else -> defaultTheme
    }
    val currentBackgroundId = themeBackgroundMap[finalTheme] ?: R.drawable.bg_light_1
    // colorScheme
    val colorScheme = getThemeColorScheme(finalTheme)

    CompositionLocalProvider(LocalAppTheme provides finalTheme) {
        WeekzTheme(
            darkTheme = finalTheme in listOf(
                AppTheme.DARK1,
                AppTheme.DARK2,
                AppTheme.DARK3,
                AppTheme.DARK4,
                AppTheme.DARK5
            ),
            dynamicColor = false
        ) {
            Surface (
                modifier = Modifier.fillMaxSize()
            ) {
                // 배경 이미지 설정
                Image(
                    painter = painterResource(id = currentBackgroundId),
                    contentDescription = "배경 이미지",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                // 내부 Composable 함수 호출
                content()
            }
        }
    }
}

@Composable
fun WeekzTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
package com.lucas.weekz.presentation.ui.sign

import android.content.Intent
import android.util.Log
import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.lucas.weekz.R
import com.lucas.weekz.presentation.theme.Black
import com.lucas.weekz.presentation.theme.LocalAppTheme
import com.lucas.weekz.presentation.theme.ThemedApp
import com.lucas.weekz.presentation.theme.Typography
import com.lucas.weekz.presentation.ui.main.MainActivity
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    val currentTheme = LocalAppTheme.current
    LaunchedEffect(key1 = true){
        Log.d("SplashScreen",currentTheme.toString())
    }
    val bigImage = if (isSystemInDarkTheme()) {
        R.drawable.img_big_black
    } else {
        R.drawable.img_big_white
    }
    Box(modifier = Modifier.fillMaxSize()) {
        val uiColor = if (isSystemInDarkTheme()) Color.White else Black
        Column(modifier = Modifier.fillMaxSize()){
            Text(
                text = "공지사항을\n캘린더에 쏙!\n내 손안의\nAI 비서",
                color = uiColor,
                style = Typography.displayLarge,
                modifier = Modifier
                    .padding(start = 30.dp, top = 100.dp)
            )
            Spacer(Modifier.weight(1f))
            Image(
                painter = painterResource(id = bigImage),
                contentDescription = "캐릭터 이미지",
                modifier = Modifier
                    .size(341.dp, 400.dp)
                    .align(Alignment.End)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_language), // 변경
                contentDescription = "언어 아이콘",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(uiColor)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "English",
                color = uiColor, // <- uiColor 적용
                style = Typography.displaySmall // <- displayMedium 스타일 적용
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "한국어",
                color = uiColor, // <- uiColor 적용
                style = Typography.displaySmall // <- displayMedium 스타일 적용
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashActivityPreview() {
    ThemedApp {
        SplashScreen()
    }
}
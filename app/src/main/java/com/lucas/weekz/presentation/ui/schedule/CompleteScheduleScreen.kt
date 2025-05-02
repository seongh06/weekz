package com.lucas.weekz.presentation.ui.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lucas.weekz.R
import com.lucas.weekz.presentation.theme.Black
import com.lucas.weekz.presentation.theme.Gray
import com.lucas.weekz.presentation.theme.ThemedApp
import com.lucas.weekz.presentation.theme.Typography
import com.lucas.weekz.presentation.ui.main.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteScheduleScreen(
    navController: NavHostController?,
    viewModel: ScheduleViewModel
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    val mediumImage = if (isSystemInDarkTheme()) {
        R.drawable.img_medium_black_1
    } else {
        R.drawable.img_medium_white_1
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 상단 텍스트 그룹
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = 100.dp), // 상단 여백 추가
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "일정 추가 중...",
                    style = Typography.bodyLarge,
                    color = uiColor,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Google Calender에 추가중",
                    style = Typography.bodyMedium,
                    color = uiColor
                )
            }

            // 캐릭터 이미지와 "새로운 일정 생성하기" 텍스트 그룹
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(
                        painter = painterResource(id = mediumImage),
                        contentDescription = "캐릭터 이미지",
                        modifier = Modifier
                            .size(200.dp, 200.dp)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        modifier = Modifier.clickable {
                            navController?.navigate(Screen.AddScheduleScreen.route)
                        },
                        text = "새로운 일정 생성하기",
                        style = Typography.bodyMedium.copy(textDecoration = TextDecoration.Underline),
                        color = Gray
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CompleteScheduleScreenPreview() {
    ThemedApp {
        CompleteScheduleScreen(navController = null, viewModel = hiltViewModel())
    }
}
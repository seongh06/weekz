package com.lucas.weekz.presentation.ui.schedule

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lucas.weekz.R
import com.lucas.weekz.presentation.theme.AppTheme
import com.lucas.weekz.presentation.theme.Gray
import com.lucas.weekz.presentation.theme.LocalAppTheme
import com.lucas.weekz.presentation.theme.Typography
import com.lucas.weekz.presentation.ui.main.Screen
import com.lucas.weekz.presentation.ui.sign.AppLanguage
import com.lucas.weekz.presentation.ui.sign.getSavedLanguageCode
import com.lucas.weekz.presentation.utill.getMediumImageForTheme
import com.lucas.weekz.presentation.utill.getSmallImageForTheme
import com.lucas.weekz.presentation.utill.getUiColorForTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FailedScheduleScreen(
    navController: NavHostController?,
    message: String?
) {
    BackHandler {
        // 시스템 뒤로가기 버튼이 눌렸을 때 아무것도 하지 않음
    }
    val context = LocalContext.current
    val uiColor = getUiColorForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조
    val smallImage = getSmallImageForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조
    val mediumImage = getMediumImageForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조    val context = LocalContext.current

   val failedMediumImage = when (LocalAppTheme.current) {
       AppTheme.DARK1, AppTheme.DARK2, AppTheme.DARK3, AppTheme.DARK4, AppTheme.DARK5 -> R.drawable.img_medium_black_failed
       else -> R.drawable.img_medium_white_failed // AppTheme.LIGHT1, LIGHT2, LIGHT3, LIGHT4 및 기타 경우
   }
    // 현재 언어 설정 가져오기
    val currentLanguage = remember {
        when (getSavedLanguageCode(context)) {
            "en" -> AppLanguage.ENGLISH
            else -> AppLanguage.KOREAN
        }
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
                    text = stringResource(
                        id = when (currentLanguage) {
                            AppLanguage.KOREAN -> R.string.failed_schedule_title_korean
                            AppLanguage.ENGLISH -> R.string.failed_schedule_title_english
                        }
                    ),
                    style = Typography.bodyLarge,
                    color = uiColor,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(
                        id = when (currentLanguage) {
                            AppLanguage.KOREAN -> R.string.failed_schedule_body_korean
                            AppLanguage.ENGLISH -> R.string.failed_schedule_body_english
                        }
                    ),
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
                        text = stringResource(
                            id = when (currentLanguage) {
                                AppLanguage.KOREAN -> R.string.failed_schedule_button_korean
                                AppLanguage.ENGLISH -> R.string.failed_schedule_button_english
                            }
                        ),
                        style = Typography.bodyMedium.copy(textDecoration = TextDecoration.Underline),
                        color = Gray
                    )
                }
            }
        }
    }
}
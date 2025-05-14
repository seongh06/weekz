package com.lucas.weekz.presentation.ui.schedule

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.lucas.weekz.R
import com.lucas.weekz.presentation.theme.Black
import com.lucas.weekz.presentation.theme.Typography
import com.lucas.weekz.presentation.ui.main.Screen
import com.lucas.weekz.presentation.ui.sign.AppLanguage
import com.lucas.weekz.presentation.ui.sign.getSavedLanguageCode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleScreen(navController: NavHostController?, viewModel: ScheduleViewModel) {
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity) // Activity 캐스팅
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    var updateDate by remember { mutableStateOf("04.25") }
    var scheduleText by remember { mutableStateOf("") }
    var geminiResponse by remember { mutableStateOf("") } // Gemini 응답을 저장할 상태 변수
    val coroutineScope = rememberCoroutineScope() // 비동기 처리를 위한 CoroutineScope

    // 현재 언어 설정 가져오기
    val currentLanguage = remember {
        when (getSavedLanguageCode(context)) {
            "en" -> AppLanguage.ENGLISH
            else -> AppLanguage.KOREAN
        }
    }

    // 시스템 뒤로가기 버튼 처리를 위한 BackHandler
    BackHandler {
        // 시스템 뒤로가기 버튼이 눌렸을 때 실행될 로직
        Log.d("BackHandler", "System back button pressed in AddScheduleScreen")
        activity?.finish() // 액티비티 종료
    }
    // scheduleText가 비어있거나 공백만 있는지 확인하는 상태 변수
    val isSendButtonEnabled = remember(scheduleText) {
        scheduleText.isNotBlank() // 공백만 있는 경우도 비활성화하려면 isNotBlank() 사용
        // scheduleText.isNotEmpty() // 비어있는 경우만 비활성화하려면 isNotEmpty() 사용
    }
    Scaffold(modifier = Modifier.fillMaxSize(),containerColor = Color.Transparent) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),// Scaffold에서 제공하는 padding을 적용합니다.
            verticalArrangement = Arrangement.Center, // 중앙 정렬을 위한 설정 추가
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Text(
                    text = stringResource(
                        id = when (currentLanguage) {
                            AppLanguage.KOREAN -> R.string.add_schedule_title_korean
                            AppLanguage.ENGLISH -> R.string.add_schedule_title_english
                        }
                    ),
                    style = Typography.bodyLarge,
                    color = uiColor,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                // 마지막 업데이트 텍스트: "마지막 업데이트" 부분만 리소스 로드, 날짜는 고정 또는 동적 처리
                Text(
                    text = "${
                        stringResource(
                        id = when (currentLanguage) {
                            AppLanguage.KOREAN -> R.string.add_schedule_last_update_korean
                            AppLanguage.ENGLISH -> R.string.add_schedule_last_update_english
                        }
                    )
                    } $updateDate", // 날짜는 예시로 하드코딩
                    style = Typography.bodyMedium,
                    color = uiColor
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            // 하단 부분 (텍스트 필드, 보내기 버튼)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 38.dp, start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(32.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = scheduleText,
                                onValueChange = { scheduleText = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = {
                                    Text(stringResource(
                                        id = when (currentLanguage) {
                                            AppLanguage.KOREAN -> R.string.add_schedule_placeholder_korean
                                            AppLanguage.ENGLISH -> R.string.add_schedule_placeholder_english
                                        }
                                    ))
                                },
                                shape = RoundedCornerShape(32.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    disabledBorderColor = Color.Transparent,
                                ),
                                textStyle = TextStyle(
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                            )
                        }

                        // 보내기 버튼
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(999.dp)
                                )
                                .padding(13.dp)
                                .size(30.dp)
                                // Box에 직접 clickable을 사용하는 대신 IconButton의 enabled 속성을 사용합니다.
                                // .clickable { Log.d("SendButton", "Send button clicked!") }
                                .then(
                                    // 버튼 비활성화 시 클릭 이벤트를 막습니다.
                                    if (isSendButtonEnabled) Modifier else Modifier.clickable(
                                        enabled = false
                                    ) {}
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(
                                onClick = {
                                    Log.d("SendButton", "Send button clicked!")
                                    viewModel.generateScheduleSummary(scheduleText)
                                    navController?.navigate(Screen.SelectScheduleScreen.route)
                                },
                                enabled = isSendButtonEnabled // 작성된 내용이 있을 때만 활성화
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_send),
                                    contentDescription = "보내기",
                                    // 버튼 비활성화 시 아이콘 색상 변경 (선택 사항)
                                    colorFilter = ColorFilter.tint(if (isSendButtonEnabled) Color.Black else Color.Gray)
                                )
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(20.dp)
                    .background(Color.Transparent)
            )
        }
    }
}
package com.lucas.weekz.presentation.ui.schedule

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.lucas.weekz.presentation.theme.LocalAppTheme
import com.lucas.weekz.presentation.theme.Typography
import com.lucas.weekz.presentation.ui.main.Screen
import com.lucas.weekz.presentation.ui.sign.AppLanguage
import com.lucas.weekz.presentation.ui.sign.SplashActivity
import com.lucas.weekz.presentation.ui.sign.getSavedLanguageCode
import com.lucas.weekz.presentation.utill.getMediumImageForTheme
import com.lucas.weekz.presentation.utill.getSmallImageForTheme
import com.lucas.weekz.presentation.utill.getUiColorForTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleScreen(navController: NavHostController?, viewModel: ScheduleViewModel) {
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity) // Activity 캐스팅
    var updateDate by remember { mutableStateOf("04.25") }
    var scheduleText by remember { mutableStateOf("") }
    var geminiResponse by remember { mutableStateOf("") } // Gemini 응답을 저장할 상태 변수
    val coroutineScope = rememberCoroutineScope() // 비동기 처리를 위한 CoroutineScope

    val isLoading by viewModel.isLoading
    val apiCallSuccess by viewModel.apiCallSuccess // ViewModel의 apiCallSuccess 관찰
    var apiCallAttempted by remember { mutableStateOf(false) } // API 호출 시도 여부 추적

    val uiColor = getUiColorForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조
    val smallImage = getSmallImageForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조
    val mediumImage = getMediumImageForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조
    // 현재 언어 설정 가져오기
    val currentLanguage = remember {
        when (getSavedLanguageCode(context)) {
            "en" -> AppLanguage.ENGLISH
            else -> AppLanguage.KOREAN
        }
    }

    LaunchedEffect(isLoading, apiCallSuccess, apiCallAttempted) {
        // 로딩이 끝났고, API 호출이 시도된 후에만 결과를 처리
        if (!isLoading && apiCallAttempted) {
            if (apiCallSuccess) {
                // 성공 시
                Toast.makeText(context, "일정 분석 성공!", Toast.LENGTH_SHORT).show()
                // TODO: 필요한 경우 여기서 viewModel.getSelectSchedule() 등을 호출하여
                //  분석된 데이터를 가져와 사용하거나, 다음 화면으로 이동
                //  예: navController.navigate(Screen.SelectScheduleScreen.route)
                //  성공 후 SelectScheduleScreen으로 이동한다고 가정
                navController?.navigate(Screen.SelectScheduleScreen.route)
            } else {
                // 실패 시
                Toast.makeText(context, "일정 분석에 실패했습니다. 입력 내용을 확인하거나 다시 시도해주세요.", Toast.LENGTH_LONG).show()
                // TODO: 필요한 경우 FailScreen으로 이동하거나 사용자에게 다른 안내 제공
                navController?.navigate(Screen.FailedScheduleScreen.route)
            }
            apiCallAttempted = false // 결과 처리 후 호출 시도 상태 초기화
        }
    }

    // 시스템 뒤로가기 버튼 처리를 위한 BackHandler
    BackHandler {
        Log.d("BackHandler", "System back button pressed in AddScheduleScreen, navigating to SplashActivity")
        // SplashActivity로 이동하는 Intent 생성
        val intent = Intent(context, SplashActivity::class.java).apply {
            // SplashActivity가 이미 스택에 있다면 새로 만들지 않고 기존 것을 사용하도록 플래그 추가 (선택 사항)
            // 이렇게 하면 SplashActivity가 중복으로 쌓이는 것을 방지할 수 있습니다.
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        context.startActivity(intent)
        activity?.finish() // 현재 AddScheduleScreen을 포함하는 액티비티는 종료
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
                                    Text(
                                        text = stringResource( // stringResource는 텍스트 내용만 가져옵니다.
                                            id = when (currentLanguage) {
                                                AppLanguage.KOREAN -> R.string.add_schedule_placeholder_korean
                                                AppLanguage.ENGLISH -> R.string.add_schedule_placeholder_english
                                            }
                                        ),
                                        style = Typography.bodyMedium
                                    )
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
                                    if (scheduleText.isNotBlank()) {
                                        apiCallAttempted = true // API 호출 시도 표시
                                        viewModel.generateScheduleSummary(scheduleText)
                                    }
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
                            if (isLoading) {
                                CircularProgressIndicator(modifier = Modifier.size(32.dp))
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
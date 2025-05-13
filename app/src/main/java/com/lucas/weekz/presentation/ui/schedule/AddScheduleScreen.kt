package com.lucas.weekz.presentation.ui.schedule

import android.util.Log
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.lucas.weekz.R
import com.lucas.weekz.presentation.theme.Black
import com.lucas.weekz.presentation.theme.Typography
import com.lucas.weekz.presentation.ui.main.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleScreen(navController: NavHostController?, viewModel: ScheduleViewModel) {
    val context = LocalContext.current
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    var updateDate by remember { mutableStateOf("04.25") }
    var scheduleText by remember { mutableStateOf("") }
    var geminiResponse by remember { mutableStateOf("") } // Gemini 응답을 저장할 상태 변수
    val coroutineScope = rememberCoroutineScope() // 비동기 처리를 위한 CoroutineScope

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
                        text = "공지사항을\n적어주세요",
                        style = Typography.bodyLarge,
                        color = uiColor,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "마지막 업데이트 $updateDate",
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
                                placeholder = { Text("일정을 작성해주세요") },
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
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(999.dp)
                                )
                                .padding(13.dp)
                                .size(30.dp)
                                .clickable {
                                    Log.d("SendButton", "Send button clicked!")
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(onClick = {
                                Log.d("SendButton", "Send button clicked!")
                                viewModel.generateScheduleSummary(scheduleText)
                                navController?.navigate(Screen.SelectScheduleScreen.route)
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_send),
                                    contentDescription = "보내기",
                                    colorFilter = ColorFilter.tint(Color.Black)
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
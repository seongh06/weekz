package com.lucas.weekz.presentation.ui.schedule

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lucas.weekz.R
import com.lucas.weekz.presentation.component.SelectScheduleCard
import com.lucas.weekz.presentation.theme.Black
import com.lucas.weekz.presentation.theme.Typography
import com.lucas.weekz.presentation.ui.sign.AppLanguage
import com.lucas.weekz.presentation.ui.sign.getSavedLanguageCode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectScheduleScreen(
    navController: NavHostController?,
    viewModel: ScheduleViewModel
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    val context = LocalContext.current
    // 현재 언어 설정 가져오기
    val currentLanguage = remember {
        when (getSavedLanguageCode(context)) {
            "en" -> AppLanguage.ENGLISH
            else -> AppLanguage.KOREAN
        }
    }

    val smallImage = if (isSystemInDarkTheme()) {
        R.drawable.img_small_black_1
    } else {
        R.drawable.img_small_white_1
    }
    // ViewModel의 상태를 remember와 by 델리게이트로 observe합니다.
    val isLoading by remember { viewModel.isLoading }
    val apiCallSuccess by remember { viewModel.apiCallSuccess }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            Box(modifier = Modifier.padding(top = 30.dp, start = 20.dp)) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                    ),
                    title = {
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController?.popBackStack()
                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = "back",
                                colorFilter = ColorFilter.tint(uiColor),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = smallImage),
                    contentDescription = "캐릭터 이미지",
                    modifier = Modifier
                        .size(50.dp, 50.dp)
                )
                Text(
                    text = stringResource(
                        id = when (currentLanguage) {
                            AppLanguage.KOREAN -> R.string.select_schedule_title_korean
                            AppLanguage.ENGLISH -> R.string.select_schedule_title_english
                        }
                    ),
                    color = uiColor,
                    modifier = Modifier.padding(top = 20.dp),
                    style = Typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.size(30.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 41.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // API 호출 로딩 중이거나 실패 상태일 때 Shimmer 플레이스홀더 표시
                if (isLoading || !apiCallSuccess) {
                    items(5) { // Shimmer 플레이스홀더를 표시할 항목 수 (예: 5개)
                        SelectScheduleCard(
                            scheduleData = null, // 데이터는 null로 전달
                            onClick = {}, // 클릭은 비활성화되므로 빈 람다 전달
                            isLoading = isLoading,
                            apiCallSuccess = apiCallSuccess
                        )
                    }
                } else if (viewModel.scheduleList.isNotEmpty()) {
                    // 데이터 로딩이 완료되고 성공했으며, 리스트에 항목이 있을 때 실제 데이터 표시
                    items(
                        count = viewModel.scheduleList.size,
                        key = { index -> viewModel.scheduleList[index].id }
                    ) { index ->
                        val scheduleData = viewModel.scheduleList[index]
                        Log.d("SelectScheduleScreen", "scheduleData: $scheduleData")
                        SelectScheduleCard(
                            scheduleData = scheduleData,
                            onClick = {
                                Log.d("SelectScheduleScreen", "SelectScheduleCard onClick triggered")
                                Log.d("SelectScheduleScreen", "setSelectSchedule before : $scheduleData")
                                if (scheduleData != null) {
                                    viewModel.setSelectSchedule(scheduleData)
                                    Log.d(
                                        "SelectScheduleScreen",
                                        "setSelectSchedule after : ${viewModel.selectSchedule}"
                                    )
                                    navController?.navigate("edit")
                                } else {
                                    Log.e("SelectScheduleScreen", "scheduleData is null!")
                                }
                            },
                            isLoading = isLoading, // 로딩 상태 전달
                            apiCallSuccess = apiCallSuccess // API 호출 상태 전달
                        )
                    }
                } else {
                    // scheduleList가 비어 있고, 로딩 중이 아니며, API 호출 성공 상태일 때
                    item {
                        Text(text = "일정이 없습니다.", color = uiColor)
                    }
                }
            }
        }
    }
}
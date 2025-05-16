package com.lucas.weekz.presentation.ui.schedule

import androidx.compose.foundation.Image
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
import com.lucas.weekz.presentation.theme.LocalAppTheme
import com.lucas.weekz.presentation.theme.Typography
import com.lucas.weekz.presentation.ui.sign.AppLanguage
import com.lucas.weekz.presentation.ui.sign.getSavedLanguageCode
import com.lucas.weekz.presentation.utill.getMediumImageForTheme
import com.lucas.weekz.presentation.utill.getSmallImageForTheme
import com.lucas.weekz.presentation.utill.getUiColorForTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectScheduleScreen(
    navController: NavHostController?,
    viewModel: ScheduleViewModel
) {
    val context = LocalContext.current
    // 현재 언어 설정 가져오기
    val currentLanguage = remember {
        when (getSavedLanguageCode(context)) {
            "en" -> AppLanguage.ENGLISH
            else -> AppLanguage.KOREAN
        }
    }
    val uiColor = getUiColorForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조
    val smallImage = getSmallImageForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조
    val mediumImage = getMediumImageForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조    val context = LocalContext.current
    val scheduleList = viewModel.scheduleList // ViewModel에서 이 리스트가 State로 관리되어야 함
    // ViewModel의 상태를 remember와 by 델리게이트로 observe합니다.
    val isLoading by remember { viewModel.isLoading }
    val apiCallSuccess by remember { viewModel.apiCallSuccess }
    val apiCallSuccessFromViewModel by remember { viewModel.apiCallSuccess } // ViewModel의 apiCallSuccess
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
                verticalArrangement = Arrangement.spacedBy(30.dp), // 비어있을 때는 의미 없음
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // 조건 1: Shimmer 표시 (로딩 중이거나, ViewModel에서 API 실패 상태일 때)
                // AddScheduleScreen에서 이미 로딩과 API 호출이 끝났다면,
                // SelectScheduleScreen 진입 시 isLoading은 false, apiCallSuccessFromViewModel은 true/false가 명확해야 함.
                if (isLoading) { // isLoading이 true이면 항상 Shimmer
                    items(5) {
                        SelectScheduleCard(
                            scheduleData = null,
                            onClick = {},
                            isLoading = true, // Shimmer이므로 true
                            apiCallSuccess = false // 로딩 중엔 성공 여부 미정
                        )
                    }
                }
                // 조건 2: API 호출이 완료되었고 (isLoading == false)
                else if (apiCallSuccessFromViewModel) {
                    // API 호출이 성공했고, 실제 데이터가 있을 때
                    if (scheduleList.isNotEmpty()) {
                        items(
                            count = scheduleList.size,
                            key = { index -> scheduleList[index].id }
                        ) { index ->
                            val scheduleData = scheduleList[index]
                            SelectScheduleCard(
                                scheduleData = scheduleData,
                                onClick = {
                                    if (scheduleData != null) { // scheduleData는 항상 not-null이지만 안전하게
                                        viewModel.setSelectSchedule(scheduleData)
                                        navController?.navigate("edit")
                                    }
                                },
                                isLoading = false, // 데이터 로드 완료
                                apiCallSuccess = true // API 호출 성공 상태
                            )
                        }
                    } else {
                        // API 호출은 성공했으나, 분석 결과 일정이 없는 경우 (Gemini가 "fail"을 반환하지는 않았지만, 파싱 후 유효한 일정이 없는 경우 등)
                        // 또는 AddScheduleScreen에서 성공 후 scheduleList에 아무것도 추가하지 않은 경우
                        item {
                            Text(
                                text = "분석 실패",
                                color = uiColor,
                                modifier = Modifier.padding(top = 50.dp)
                            )
                        }
                    }
                }
                // 조건 3: API 호출이 완료되었고 (isLoading == false), 실패했을 때
                else { // (isLoading == false && !apiCallSuccessFromViewModel)
                    // 이 경우, AddScheduleScreen에서 API 호출 실패 후 이 화면으로 왔을 때 해당될 수 있습니다.
                    // 또는 SelectScheduleScreen이 자체적으로 데이터를 로드하려다 실패한 경우.
                    // 기존 코드에서는 이 경우에도 Shimmer를 보여줬지만,
                    // "일정이 없습니다" 또는 "데이터 로드 실패" 메시지를 보여주는 것이 더 적절할 수 있습니다.
                    // 여기서는 "일정이 없습니다"로 통일 (Shimmer를 원하면 if (isLoading || !apiCallSuccessFromViewModel) 사용)
                    item {
                        Text(
                            text = "데이터 로드 실패",
                            color = uiColor,
                            modifier = Modifier.padding(top = 50.dp)
                        )
                    }
                    // 기존 Shimmer 로직을 유지하고 싶다면, 이 else 블록을 삭제하고
                    // 최상단의 if 조건을 (isLoading || !apiCallSuccessFromViewModel) 로 되돌립니다.
                    // 하지만 이렇게 하면 API 실패 시에도 Shimmer가 계속 보일 수 있습니다.
                }
            }
        }
    }
}
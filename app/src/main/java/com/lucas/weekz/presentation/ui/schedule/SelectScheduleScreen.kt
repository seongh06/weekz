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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lucas.weekz.R
import com.lucas.weekz.presentation.component.SelectScheduleCard
import com.lucas.weekz.presentation.theme.Black
import com.lucas.weekz.presentation.theme.ThemedApp
import com.lucas.weekz.presentation.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectScheduleScreen(
    navController: NavHostController?,
    viewModel: ScheduleViewModel
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    val smallImage = if (isSystemInDarkTheme()) {
        R.drawable.img_small_black_1
    } else {
        R.drawable.img_small_white_1
    }

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
                    text = "일정 선택",
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
                if (viewModel.scheduleList.isNotEmpty()) {
                    items(
                        count = viewModel.scheduleList.size, // 변경
                        key = { index -> viewModel.scheduleList[index].id } //key 파라미터 추가
                    ) { index ->
                        val scheduleData = viewModel.scheduleList[index] // 변경
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
                            }
                        )
                    }
                } else {
                    // scheduleList가 비어 있을 때 보여줄 내용
                    item {
                        Text(text = "일정이 없습니다.", color = uiColor)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SelectScheduleScreenPreview() {
    ThemedApp {
        SelectScheduleScreen(navController = null, viewModel = hiltViewModel())
    }
}
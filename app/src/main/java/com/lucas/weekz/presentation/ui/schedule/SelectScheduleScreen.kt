package com.lucas.weekz.presentation.ui.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lucas.weekz.presentation.component.SelectScheduleCard
import com.lucas.weekz.presentation.theme.ThemedApp

@Composable
fun SelectScheduleScreen(
    navController: NavHostController?,
    viewModel: SelectScheduleViewModel = hiltViewModel()
) {
    val scheduleList = viewModel.getScheduleList()
    val scheduleListCount = viewModel.getScheduleListCount()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), // Scaffold에서 제공하는 padding을 적용합니다.
            verticalArrangement = Arrangement.Center, // 중앙 정렬을 위한 설정 추가
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                LazyColumn {
                    if (scheduleList.isNotEmpty()) {
                        items(count = scheduleListCount) { index ->
                            val frameData = scheduleList[index]
                            SelectScheduleCard(scheduleData = frameData)
                        }
                    } else {
                        // scheduleList가 비어 있을 때 보여줄 내용
                        item {
                            Text(text = "일정이 없습니다.", color = Color.Black)
                        }
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
        SelectScheduleScreen(navController = null)
    }
}
package com.lucas.weekz.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lucas.weekz.presentation.theme.Black
import com.lucas.weekz.presentation.ui.schedule.ScheduleData

@Composable
fun SelectScheduleCard(scheduleData: ScheduleData) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .width(330.dp)
            .height(165.dp)
            .background(color = Color.White, shape = RoundedCornerShape(size = 20.dp))
            .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 15.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()){
            Text(text = "제목 | ", color = Black)
            Text(text = scheduleData.title, color = Black)
        }
        Row(modifier = Modifier.fillMaxWidth()){
            Text(text = "날짜 | ", color = Black)
            Text(text = scheduleData.date, color = Black)
        }
        Row(modifier = Modifier.fillMaxWidth()){
            Text(text = "시간 | ", color = Black)
            Text(text = scheduleData.time, color = Black)
        }
        Row(modifier = Modifier.fillMaxWidth()){
            Text(text = "장소 | ", color = Black)
            Text(text = scheduleData.location, color = Black)
        }
        Row(modifier = Modifier.fillMaxWidth()){
            Text(text = "메모 | ", color = Black)
            Text(text = scheduleData.memo, color = Black)
        }
    }
}

@Preview
@Composable
fun SelectScheduleCardPreview() {
    val sampleSchedule = ScheduleData(
        title = "예시 제목",
        date = "2025.00.00 ~ 2025.00.00",
        time = "24:00",
        location = "예시 장소",
        memo = "예시 메모"
    )
    SelectScheduleCard(scheduleData = sampleSchedule)
}
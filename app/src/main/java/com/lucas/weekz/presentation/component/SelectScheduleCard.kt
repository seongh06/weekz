package com.lucas.weekz.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lucas.weekz.data.dto.ScheduleDto
import com.lucas.weekz.presentation.theme.Black
import com.lucas.weekz.presentation.theme.Typography
import com.lucas.weekz.presentation.theme.White

@Composable
fun SelectScheduleCard(
    modifier: Modifier = Modifier,
    scheduleData: ScheduleDto,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(White)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row {
                Text(
                    text = "제목",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = scheduleData.title,
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row {
                Text(
                    text = "날짜",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = scheduleData.date,
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row {
                Text(
                    text = "시간",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = scheduleData.time,
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row {
                Text(
                    text = "장소",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = scheduleData.location,
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row {
                Text(
                    text = "메모",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = scheduleData.memo,
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Preview
@Composable
fun SelectScheduleCardPreview() {
    val sampleSchedule = ScheduleDto(
        title = "예시 제목",
        date = "2025.00.00 ~ 2025.00.00",
        time = "24:00",
        location = "예시 장소",
        memo = "예시 메모"
    )
    SelectScheduleCard(scheduleData = sampleSchedule, onClick = {})
}
package com.lucas.weekz.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lucas.weekz.presentation.theme.Black
import com.lucas.weekz.presentation.theme.Typography
import com.lucas.weekz.presentation.theme.White
import com.lucas.weekz.presentation.ui.schedule.ScheduleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScheduleCard(
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(White)
            .padding(horizontal = 20.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "제목",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(20.dp))
                OutlinedTextField(
                    value = viewModel.title.value,
                    onValueChange = {
                        viewModel.updateScheduleData(
                            newTitle = it,
                            newDate = viewModel.date.value,
                            newTime = viewModel.time.value,
                            newLocation = viewModel.location.value,
                            newMemo = viewModel.memo.value
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(19.dp),
                    textStyle = Typography.bodyMedium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "날짜",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(20.dp))
                OutlinedTextField(
                    value = viewModel.date.value,
                    onValueChange = {
                        viewModel.updateScheduleData(
                            newTitle = viewModel.title.value,
                            newDate = it,
                            newTime = viewModel.time.value,
                            newLocation = viewModel.location.value,
                            newMemo = viewModel.memo.value
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(19.dp),
                    textStyle = Typography.bodyMedium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "시간",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(20.dp))
                OutlinedTextField(
                    value = viewModel.time.value,
                    onValueChange = {
                        viewModel.updateScheduleData(
                            newTitle = viewModel.title.value,
                            newDate = viewModel.date.value,
                            newTime = it,
                            newLocation = viewModel.location.value,
                            newMemo = viewModel.memo.value
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(19.dp),
                    textStyle = Typography.bodyMedium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "장소",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(20.dp))
                OutlinedTextField(
                    value = viewModel.location.value,
                    onValueChange = {
                        viewModel.updateScheduleData(
                            newTitle = viewModel.title.value,
                            newDate = viewModel.date.value,
                            newTime = viewModel.time.value,
                            newLocation = it,
                            newMemo = viewModel.memo.value
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(19.dp),
                    textStyle = Typography.bodyMedium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "메모",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(20.dp))
                OutlinedTextField(
                    value = viewModel.memo.value,
                    onValueChange = {
                        viewModel.updateScheduleData(
                            newTitle = viewModel.title.value,
                            newDate = viewModel.date.value,
                            newTime = viewModel.time.value,
                            newLocation = viewModel.location.value,
                            newMemo = it
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(19.dp),
                    textStyle = Typography.bodyMedium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent
                    )
                )
            }
        }
    }
}
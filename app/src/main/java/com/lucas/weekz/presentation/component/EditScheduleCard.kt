package com.lucas.weekz.presentation.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Title",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(65.dp) // 레이블의 너비를 고정
                )
                Spacer(modifier = Modifier.size(15.dp))
                Box(
                    modifier = Modifier.fillMaxWidth()
                ){ OutlinedTextField(
                        value = viewModel.title.value.also { Log.d("EditScheduleCard", "title value: $it") }, // 로그 추가
                        onValueChange = {
                            viewModel.updateTitleData(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                    placeholder = { Text("") },
                    textStyle = Typography.bodyMedium.copy(color = Black),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.size(-20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Date",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(65.dp) // 레이블의 너비를 고정
                )
                Spacer(modifier = Modifier.size(15.dp))
                Box(
                    modifier = Modifier.fillMaxWidth()
                ){
                    OutlinedTextField(
                        value = viewModel.date.value.also { Log.d("EditScheduleCard", "date value: $it") }, // 로그 추가
                        onValueChange = {
                            viewModel.updateDateData(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = { Text("") },
                        textStyle = Typography.bodyMedium.copy(color = Black),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.size(-20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Time",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(65.dp) // 레이블의 너비를 고정
                )
                Spacer(modifier = Modifier.size(15.dp))
                Box(
                    modifier = Modifier.fillMaxWidth()
                ){
                    OutlinedTextField(
                        value = viewModel.time.value.also { Log.d("EditScheduleCard", "time value: $it") }, // 로그 추가
                        onValueChange = {
                            viewModel.updateTimeData(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = { Text("") },
                        textStyle = Typography.bodyMedium.copy(color = Black),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.size(-20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Location",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(65.dp) // 레이블의 너비를 고정
                )
                Spacer(modifier = Modifier.size(15.dp))
                Box(
                    modifier = Modifier.fillMaxWidth()
                ){
                    OutlinedTextField(
                        value = viewModel.location.value.also { Log.d("EditScheduleCard", "location value: $it") }, // 로그 추가
                        onValueChange = {
                            viewModel.updateLocationData(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = { Text("") },
                        textStyle = Typography.bodyMedium.copy(color = Black),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.size(-20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Memo",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(65.dp) // 레이블의 너비를 고정
                )
                Spacer(modifier = Modifier.size(15.dp))
                Box(
                    modifier = Modifier.fillMaxWidth()
                ){
                    OutlinedTextField(
                        value = viewModel.memo.value.also { Log.d("EditScheduleCard", "memo value: $it") }, // 로그 추가
                        onValueChange = {
                            Log.d("EditScheduleCard", "onValueChange - memo: $it")
                            viewModel.updateMemoData(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = { Text("") },
                        textStyle = Typography.bodyMedium.copy(color = Black),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
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
}
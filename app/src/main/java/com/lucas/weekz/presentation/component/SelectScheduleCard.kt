package com.lucas.weekz.presentation.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.lucas.weekz.data.dto.ScheduleDto
import com.lucas.weekz.presentation.theme.Black
import com.lucas.weekz.presentation.theme.Typography
import com.lucas.weekz.presentation.theme.White

@Composable
fun SelectScheduleCard(
    modifier: Modifier = Modifier,
    scheduleData: ScheduleDto?,
    onClick: (ScheduleDto?) -> Unit, // onClick 람다도 nullable ScheduleDto를 받도록 변경
    isLoading: Boolean = false,
    apiCallSuccess: Boolean = true
) {
    val showShimmer = isLoading || !apiCallSuccess
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(White)
            .clickable(enabled = !isLoading && apiCallSuccess) { // 로딩 중 또는 실패 시 클릭 비활성화
                onClick(scheduleData) // scheduleData를 nullable로 전달
            }
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
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(60.dp) // 레이블의 너비를 고정하여 Shimmer 크기 예측
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = scheduleData?.title ?: "----------", // 로딩 중일 때 표시할 플레이스홀더 텍스트
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f) // 남은 공간을 채우도록 weight 적용
                        .placeholder(
                            visible = showShimmer,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.Gray.copy(alpha = 0.3f), // Shimmer 효과 색상 (어두운 테마 고려)
                                animationSpec = infiniteRepeatable(
                                    animation = TweenSpec(durationMillis = 1000),
                                    repeatMode = RepeatMode.Reverse
                                )
                            ),
                            color = Color.LightGray.copy(alpha = 0.2f) // 플레이스홀더 기본 색상
                        )
                )
            }
            Spacer(modifier = Modifier.height(10.dp)) // height로 변경
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "날짜",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(60.dp) // 레이블의 너비를 고정
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = scheduleData?.date ?: "----------",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .placeholder(
                            visible = showShimmer,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.Gray.copy(alpha = 0.3f),
                                animationSpec = infiniteRepeatable(
                                    animation = TweenSpec(durationMillis = 1000),
                                    repeatMode = RepeatMode.Reverse
                                )
                            ),
                            color = Color.LightGray.copy(alpha = 0.2f)
                        )
                )
            }
            Spacer(modifier = Modifier.height(10.dp)) // height로 변경
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "시간",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(60.dp) // 레이블의 너비를 고정
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = scheduleData?.time ?: "----------",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .placeholder(
                            visible = showShimmer,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.Gray.copy(alpha = 0.3f),
                                animationSpec = infiniteRepeatable(
                                    animation = TweenSpec(durationMillis = 1000),
                                    repeatMode = RepeatMode.Reverse
                                )
                            ),
                            color = Color.LightGray.copy(alpha = 0.2f)
                        )
                )
            }
            Spacer(modifier = Modifier.height(10.dp)) // height로 변경
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "장소",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(60.dp) // 레이블의 너비를 고정
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = scheduleData?.location ?: "----------",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .placeholder(
                            visible = showShimmer,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.Gray.copy(alpha = 0.3f),
                                animationSpec = infiniteRepeatable(
                                    animation = TweenSpec(durationMillis = 1000),
                                    repeatMode = RepeatMode.Reverse
                                )
                            ),
                            color = Color.LightGray.copy(alpha = 0.2f)
                        )
                )
            }
            Spacer(modifier = Modifier.height(10.dp)) // height로 변경
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "메모",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(60.dp) // 레이블의 너비를 고정
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = scheduleData?.memo ?: "----------",
                    color = Black,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .placeholder(
                            visible = showShimmer,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.Gray.copy(alpha = 0.3f),
                                animationSpec = infiniteRepeatable(
                                    animation = TweenSpec(durationMillis = 1000),
                                    repeatMode = RepeatMode.Reverse
                                )
                            ),
                            color = Color.LightGray.copy(alpha = 0.2f)
                        )
                )
            }
        }
    }
}
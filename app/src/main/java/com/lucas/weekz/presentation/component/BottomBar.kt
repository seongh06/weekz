package com.lucas.weekz.presentation.component

import androidx.appcompat.widget.AppCompatImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.lucas.weekz.R

@Composable
fun GradientVectorIcon(modifier: Modifier = Modifier, drawableResId: Int, contentDescription: String?) {
    val context = LocalContext.current
    AndroidView(
        factory = { ctx ->
            AppCompatImageView(ctx).apply {
                setImageResource(drawableResId)
            }
        },
        modifier = modifier,
        update = { view ->
            view.setImageResource(drawableResId)
            view.contentDescription = contentDescription
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(
    currentRoute: String,
    onHomeClick: () -> Unit,
    onWeekzClick: () -> Unit,
    onTaskClick: () -> Unit,
    selectedItemColor: Color = MaterialTheme.colorScheme.primary, // 선택된 아이템 색상
    unselectedItemColor: Color = MaterialTheme.colorScheme.onSurfaceVariant, // 선택되지 않은 아이템 색상
    weekzTextColor: Color = Color.Black // WEEKZ 텍스트 색상 파라미터 추가
) {
    Box(
        modifier = Modifier.fillMaxWidth().height(117.dp)
    )  {
        // 1. 배경 이미지 (bg_bottombar.xml)
        Image(
            painter = painterResource(id = R.drawable.bg_bottombar),
            contentDescription = "Bottom bar background",
            modifier = Modifier
                .fillMaxWidth()
                .height(117.dp)
                // 배경 XML의 viewportHeight를 고려하여 높이 설정 또는 matchParentSize
                // .height(100.dp) // 예시 높이, 실제 XML 비율에 맞게 조절해야 함
                .matchParentSize(), // Box 크기에 맞춤 (Box가 크기를 가져야 함)
            contentScale = ContentScale.FillBounds // 배경이 Box를 꽉 채우도록
        )

        // 2. 아이템들을 포함하는 Row (배경 이미지 위에 그려짐)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
                // XML 배경의 실제 아이콘 배치 영역을 고려하여 Row의 전체적인 수직 정렬 조정
                .align(Alignment.TopCenter), // Box의 중앙에 Row를 배치 (배경 XML 디자인에 따라 Bottom, Top 등 조절)
            horizontalArrangement = Arrangement.SpaceAround, // 아이템 간 간격 균등 배분
            verticalAlignment = Alignment.CenterVertically // 아이템들을 수직 중앙 정렬
        ) {
            // 왼쪽: Home
            BottomBarItem(
                text = "Home",
                drawableResId = R.drawable.ic_home,
                onClick = onHomeClick,
                modifier = Modifier.weight(1f).padding(top = 14.dp),
                selected = currentRoute == "home",
                selectedColor = selectedItemColor,
                unselectedColor = unselectedItemColor
            )

            // 가운데: WEEKZ (아이콘과 텍스트만, 배경/모양은 XML이 담당)
            Column(
                modifier = Modifier
                    .weight(1f) // 다른 아이템들과 공간을 균등하게 차지하도록
                    .clickable { onWeekzClick() }
                    .align(Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_bottom_weekz), // PNG
                    contentDescription = "WEEKZ",
                    modifier = Modifier.size(65.dp) // 요청하신 크기
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "WEEKZ",
                    color = weekzTextColor, // 파라미터로 받은 색상 사용
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp // 크기는 필요에 따라 조절
                )
            }

            // 오른쪽: Task
            BottomBarItem(
                text = "Task",
                drawableResId = R.drawable.ic_task,
                onClick = onTaskClick,
                modifier = Modifier.weight(1f).padding(top = 14.dp),
                selected = currentRoute == "task",
                selectedColor = selectedItemColor,
                unselectedColor = unselectedItemColor
            )
        }
    }
}

@Composable
fun RowScope.BottomBarItem(
    text: String,
    drawableResId: Int, // ImageVector 대신 Int 타입의 리소스 ID를 받도록 변경
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    selectedColor: Color = Color.Black, // 이 색상은 GradientVectorIcon에 직접 적용되지는 않음
    unselectedColor: Color = Color.Gray  // 이 색상은 GradientVectorIcon에 직접 적용되지는 않음
) {
    // GradientVectorIcon은 자체 XML의 그래디언트를 사용하므로,
    // selectedColor/unselectedColor는 Text에만 적용하거나 다른 방식으로 활용해야 합니다.
    val textColor = if (selected) selectedColor else unselectedColor

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        GradientVectorIcon( // Icon 대신 GradientVectorIcon 사용
            drawableResId = drawableResId,
            contentDescription = text,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = textColor, // 텍스트 색상은 선택 상태에 따라 변경
            fontWeight = FontWeight.Bold
        )
        // 아래 중복된 GradientVectorIcon 호출 제거됨
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFCCCCCC) // 배경색 변경하여 XML 잘 보이는지 확인
@Composable
fun BottomBarWithXmlBackgroundPreview() {
    MaterialTheme {
        BottomBar(
            currentRoute = "home",
            onHomeClick = { },
            onWeekzClick = { },
            onTaskClick = { },
            weekzTextColor = Color.DarkGray // Preview에서 WEEKZ 텍스트 색상 확인
        )
    }
}
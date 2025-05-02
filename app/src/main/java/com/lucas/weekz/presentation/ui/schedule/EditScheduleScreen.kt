package com.lucas.weekz.presentation.ui.schedule

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.lucas.weekz.presentation.component.EditScheduleCard
import com.lucas.weekz.presentation.theme.Black
import com.lucas.weekz.presentation.theme.ThemedApp
import com.lucas.weekz.presentation.theme.Typography
import com.lucas.weekz.presentation.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScheduleScreen(
    navController: NavHostController?,
    viewModel: ScheduleViewModel
) {
    Log.d("EditScheduleScreen", "EditScheduleScreen Entered")

    val selectSchedule = viewModel.selectSchedule.value
    Log.d("EditScheduleScreen", "selectSchedule : $selectSchedule")

    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    val smallImage = if (isSystemInDarkTheme()) {
        R.drawable.img_small_white_1
    } else {
        R.drawable.img_small_white_1
    }
    LaunchedEffect(selectSchedule) {
        if(selectSchedule != null){
            viewModel.updateScheduleData(selectSchedule.title,selectSchedule.date,selectSchedule.time,selectSchedule.location,selectSchedule.memo)
        }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 41.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
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
                        text = "일정 편집",
                        color = uiColor,
                        modifier = Modifier.padding(top = 20.dp),
                        style = Typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.size(30.dp))

                EditScheduleCard(
                    viewModel = viewModel
                )
            }
            Button(
                onClick = {
                    navController?.popBackStack()
                },
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 20.dp)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = White
                ),
                shape = RoundedCornerShape(999.dp)
            ) {
                Text(
                    text = "확정",
                    color = Black,
                    style = Typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.size(50.dp))
        }
    }
}
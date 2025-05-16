package com.lucas.weekz.presentation.ui.schedule

import android.content.Intent
import android.provider.CalendarContract
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.lucas.weekz.presentation.component.EditScheduleCard
import com.lucas.weekz.presentation.theme.Black
import com.lucas.weekz.presentation.theme.LocalAppTheme
import com.lucas.weekz.presentation.theme.Typography
import com.lucas.weekz.presentation.theme.White
import com.lucas.weekz.presentation.ui.main.Screen
import com.lucas.weekz.presentation.ui.sign.AppLanguage
import com.lucas.weekz.presentation.ui.sign.getSavedLanguageCode
import com.lucas.weekz.presentation.utill.getMediumImageForTheme
import com.lucas.weekz.presentation.utill.getSmallImageForTheme
import com.lucas.weekz.presentation.utill.getUiColorForTheme
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun parseDateTimeToMillis(date: String, time: String): Long {
    // 날짜와 시간을 하나의 문자열로 합칩니다.
    // 실제 형식에 따라 구분자나 순서를 조정해야 할 수 있습니다.
    val dateTimeString = "$date $time"
    // SimpleDateFormat을 사용하여 문자열을 Date 객체로 파싱합니다.
    // 실제 형식에 맞게 패턴을 조정해야 합니다. 예: "yyyy.MM.dd HH:mm"
    val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault()) // Locale.getDefault() 오류 해결
    return try {
        val dateObj: Date? = dateFormat.parse(dateTimeString)
        dateObj?.time ?: 0L // Date 객체를 밀리초로 변환, 파싱 실패 시 0L 반환
    } catch (e: ParseException) {
        Log.e("EditScheduleScreen", "Error parsing date/time: $dateTimeString", e)
        0L // 파싱 실패 시 0L 반환
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScheduleScreen(
    navController: NavHostController?,
    viewModel: ScheduleViewModel
) {
    Log.d("EditScheduleScreen", "EditScheduleScreen Entered")

    val selectSchedule = viewModel.selectSchedule.value

    val editedTitle by remember { viewModel.title }
    val editedDate by remember { viewModel.date }
    val editedTime by remember { viewModel.time }
    val editedLocation by remember { viewModel.location }
    val editedMemo by remember { viewModel.memo }
    val context = LocalContext.current
    Log.d("EditScheduleScreen", "selectSchedule : $selectSchedule")

    val uiColor = getUiColorForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조
    val smallImage = getSmallImageForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조
    val mediumImage = getMediumImageForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조    val context = LocalContext.current

    // 현재 언어 설정 가져오기
    val currentLanguage = remember {
        when (getSavedLanguageCode(context)) {
            "en" -> AppLanguage.ENGLISH
            else -> AppLanguage.KOREAN
        }
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
                        text = stringResource(
                            id = when (currentLanguage) {
                                AppLanguage.KOREAN -> R.string.edit_schedule_title_korean
                                AppLanguage.ENGLISH -> R.string.edit_schedule_title_english
                            }
                        ),
                        color = uiColor,
                        modifier = Modifier.padding(top = 20.dp),
                        style = Typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.size(30.dp))

                EditScheduleCard(
                    viewModel = viewModel
                )
                Button(
                    onClick = {
                        // "확정" 버튼 클릭 시 Google Calendar Intent 실행
                        val intent = Intent(Intent.ACTION_INSERT).apply {
                            data = CalendarContract.Events.CONTENT_URI
                            putExtra(CalendarContract.Events.TITLE, editedTitle)

                            // editedDate 및 editedTime을 파싱하여 밀리초 단위 시간으로 변환
                            // "2024.01.01 ~ 2024.01.02" 형식의 날짜 처리
                            val dateParts = editedDate.split("~").map { it.trim() }
                            val startDateString = dateParts.first()
                            val endDateString = if (dateParts.size > 1) dateParts.last() else startDateString // 종료 날짜가 없으면 시작 날짜와 동일

                            // 시작 시간 파싱
                            val startTimeMillis = parseDateTimeToMillis(startDateString, editedTime)
                            // 종료 시간 파싱 (시간 형식이 동일하다 가정)
                            val endTimeMillis = parseDateTimeToMillis(endDateString, editedTime)

                            // 파싱 결과가 유효할 때만 시간 정보 추가
                            if (startTimeMillis > 0) {
                                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTimeMillis)
                                // 종료 시간도 유효하면 추가
                                if (endTimeMillis > 0 && endTimeMillis >= startTimeMillis) { // 종료 시간이 시작 시간보다 같거나 커야 유효
                                    putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTimeMillis)
                                } else {
                                    // 종료 시간 파싱 실패 또는 시작 시간보다 이전이면 기본값 설정 (예: 시작 시간 + 1시간)
                                    putExtra(CalendarContract.EXTRA_EVENT_END_TIME, startTimeMillis + 60 * 60 * 1000)
                                }

                                // 종일 일정 여부는 시간 정보가 있을 때 false
                                putExtra(CalendarContract.Events.ALL_DAY, false)
                            } else {
                                // 날짜/시간 파싱 실패 시, 필요에 따라 종일 일정으로 설정하거나 기본값 처리
                                // putExtra(CalendarContract.Events.ALL_DAY, true) // 예: 종일 일정으로 설정
                                Log.w("EditScheduleScreen", "Failed to parse date/time, adding as all-day event or skipping time.")
                                // 시간 정보를 추가하지 않으면 종일 일정으로 처리될 수 있습니다.
                            }


                            putExtra(CalendarContract.Events.EVENT_LOCATION, editedLocation)
                            putExtra(CalendarContract.Events.DESCRIPTION, editedMemo)


                            // 그 외 필요한 putExtra 설정 추가 가능 (예: setAvailability, setGuestsCanModify)
                        }
                        // Intent를 실행하여 Google Calendar 앱으로 이동
                        try {
                            context.startActivity(intent)
                            // Google Calendar 앱으로 이동 후 EditScheduleScreen을 종료하거나 다른 화면으로 이동
                            // 여기서는 CompleteScheduleScreen으로 이동하는 원래 로직을 유지합니다.
                            navController?.navigate(Screen.CompleteScheduleScreen.route)
                        } catch (e: Exception) {
                            Log.e("EditScheduleScreen", "Failed to open calendar app", e)
                            // 캘린더 앱이 설치되지 않았거나 Intent 처리 중 오류 발생 시 사용자에게 알림
                            // 예: Toast.makeText(context, "캘린더 앱을 열 수 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White
                    ),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text(
                        text = stringResource(
                            id = when (currentLanguage) {
                                AppLanguage.KOREAN -> R.string.edit_schedule_confirm_button_korean
                                AppLanguage.ENGLISH -> R.string.edit_schedule_confirm_button_english
                            }
                        ),
                        color = Black,
                        style = Typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}
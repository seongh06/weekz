package com.lucas.weekz.presentation.ui.setting

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.lucas.weekz.presentation.theme.AppTheme
import com.lucas.weekz.presentation.theme.LocalAppTheme
import com.lucas.weekz.presentation.theme.Typography
import com.lucas.weekz.presentation.ui.sign.AppLanguage
import com.lucas.weekz.presentation.ui.sign.SplashActivity
import com.lucas.weekz.presentation.ui.sign.getSavedLanguageCode
import com.lucas.weekz.presentation.utill.getMediumImageForTheme
import com.lucas.weekz.presentation.utill.getSmallImageForTheme
import com.lucas.weekz.presentation.utill.getUiColorForTheme

// 테마 변경을 처리할 함수를 외부에서 주입받도록 수정
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SettingThemeScreen(
    navController: NavHostController?,
    onThemeSelected: (selectedTheme: AppTheme) -> Unit // 콜백 파라미터를 AppTheme으로 변경
) {
    val context = LocalContext.current
    // 현재 언어 설정 가져오기
    val currentLanguage = remember {
        when (getSavedLanguageCode(context)) {
            "en" -> AppLanguage.ENGLISH
            else -> AppLanguage.KOREAN
        }
    }
    val uiColor = getUiColorForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조
    val smallImage = getSmallImageForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조
    val mediumImage = getMediumImageForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조
    // 가로 스크롤 상태 기억
    val lightThemeScrollState = rememberScrollState()
    val darkThemeScrollState = rememberScrollState()

    // Light 테마 이미지 리소스와 AppTheme 매핑
    val lightThemes = listOf(
        AppTheme.LIGHT1 to R.drawable.img_theme_light_1,
        AppTheme.LIGHT2 to R.drawable.img_theme_light_2,
        AppTheme.LIGHT3 to R.drawable.img_theme_light_3,
        AppTheme.LIGHT4 to R.drawable.img_theme_light_4
    )

    // Dark 테마 이미지 리소스와 AppTheme 매핑
    val darkThemes = listOf(
        AppTheme.DARK1 to R.drawable.img_theme_dark_1,
        AppTheme.DARK2 to R.drawable.img_theme_dark_2,
        AppTheme.DARK3 to R.drawable.img_theme_dark_3,
        AppTheme.DARK4 to R.drawable.img_theme_dark_4,
        AppTheme.DARK5 to R.drawable.img_theme_dark_5
    )

    // Light 테마 이미지 리소스 (4개)
    val lightThemeImages = listOf(
        R.drawable.img_theme_light_1, // 예시 이미지 1
        R.drawable.img_theme_light_2, // 예시 이미지 2
        R.drawable.img_theme_light_3, // 예시 이미지 3
        R.drawable.img_theme_light_4  // 예시 이미지 4
    )

    // Dark 테마 이미지 리소스 (5개)
    val darkThemeImages = listOf(
        R.drawable.img_theme_dark_1, // 예시 이미지 1
        R.drawable.img_theme_dark_2, // 예시 이미지 2
        R.drawable.img_theme_dark_3, // 예시 이미지 3
        R.drawable.img_theme_dark_4, // 예시 이미지 4
        R.drawable.img_theme_dark_5  // 예시 이미지 5
    )

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
                            val intent = Intent(context, SplashActivity::class.java)
                            context.startActivity(intent)

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
                    text = stringResource(
                        id = when (currentLanguage) {
                            AppLanguage.KOREAN -> R.string.select_schedule_title_korean
                            AppLanguage.ENGLISH -> R.string.select_schedule_title_english
                        }
                    ),
                    color = uiColor,
                    modifier = Modifier.padding(top = 20.dp),
                    style = Typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.size(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // "Light" 텍스트
                Text(
                    text ="Light.ver",
                    color = uiColor,
                    style = Typography.displayMedium // 적절한 스타일 선택
                )

                // Light 테마 이미지 가로 스크롤 영역 (4개 이미지)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(lightThemeScrollState), // Light 테마 스크롤 상태 적용
                    horizontalArrangement = Arrangement.spacedBy(16.dp), // 이미지 간 간격
                    verticalAlignment = Alignment.CenterVertically // 이미지 세로 중앙 정렬
                ) {
                    lightThemes.forEach { (theme, imageResId) -> // AppTheme과 이미지 리소스 ID를 함께 사용
                        Image(
                            painter = painterResource(id = imageResId),
                            contentDescription = "light theme ${theme.name}", // contentDescription에 테마 이름 추가
                            modifier = Modifier
                                .size(103.dp, 223.dp)
                                .clickable {
                                    onThemeSelected(theme) // 선택된 AppTheme 전달
                                }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.size(30.dp))
            // Dark 테마 선택 영역
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp) // 텍스트와 이미지 간 간격
            ) {
                // "Dark" 텍스트
                Text(
                    text = "Dark.ver",
                    color = uiColor,
                    style =Typography.displayMedium // 적절한 스타일 선택
                )

                // Dark 테마 이미지 가로 스크롤 영역 (5개 이미지)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(darkThemeScrollState), // Dark 테마 스크롤 상태 적용
                    horizontalArrangement = Arrangement.spacedBy(16.dp), // 이미지 간 간격
                    verticalAlignment = Alignment.CenterVertically // 이미지 세로 중앙 정렬
                ) {
                    darkThemes.forEach { (theme, imageResId) -> // AppTheme과 이미지 리소스 ID를 함께 사용
                        Image(
                            painter = painterResource(id = imageResId),
                            contentDescription = "dark theme ${theme.name}", // contentDescription에 테마 이름 추가
                            modifier = Modifier
                                .size(103.dp, 223.dp)
                                .clickable {
                                    onThemeSelected(theme) // 선택된 AppTheme 전달
                                }
                        )
                    }
                }
            }
        }
    }
}
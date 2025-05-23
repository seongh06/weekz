package com.lucas.weekz.presentation.ui.sign
import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lucas.weekz.R
import com.lucas.weekz.presentation.theme.LocalAppTheme
import com.lucas.weekz.presentation.theme.Typography
import com.lucas.weekz.presentation.utill.getLargeImageForTheme
import com.lucas.weekz.presentation.utill.getMediumImageForTheme
import com.lucas.weekz.presentation.utill.getOppositeUiColorForTheme
import com.lucas.weekz.presentation.utill.getSmallImageForTheme
import com.lucas.weekz.presentation.utill.getUiColorForTheme


// SharedPreferences에 언어 코드를 저장하는 키
private const val LANGUAGE_PREF_KEY = "app_language"

// SharedPreferences에 언어 코드를 저장하는 함수 (새로 추가 또는 수정)
fun saveLanguageCode(context: Context, languageCode: String) {
    val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
    with(sharedPrefs.edit()) {
        putString(LANGUAGE_PREF_KEY, languageCode)
        apply() // 비동기로 저장
    }
}

// SharedPreferences에서 저장된 언어 코드를 로드하는 함수
fun getSavedLanguageCode(context: Context): String? {
    val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
    return sharedPrefs.getString(LANGUAGE_PREF_KEY, "ko") ?: "ko"
}

// 언어 상태를 나타내는 enum 클래스 (stringResource ID를 반환하는 함수 포함)
enum class AppLanguage(val code: String) {
    KOREAN("ko"), ENGLISH("en");

    // 해당 언어의 splash_title 리소스 ID를 반환
    fun getSplashTitleResourceId(): Int {
        return when (this) {
            KOREAN -> R.string.splash_title_korean
            ENGLISH -> R.string.splash_title_english
        }
    }
    // 해당 언어의 language_text 리소스 ID를 반환 (옵션)
    fun getLanguageTextResourceId(): Int {
        return when (this) {
            KOREAN -> R.string.language_korean
            ENGLISH -> R.string.language_english
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(navController: NavHostController? = null,
        onBoxClick: () -> Unit, onSettingsClick: () -> Unit // 설정 아이콘 클릭 시 실행될 람다 함수 파라미터 추가
) { // Activity를 인자로 받도록 수정
    val context = LocalContext.current
    val currentTheme = LocalAppTheme.current

    LaunchedEffect(key1 = true) {
        Log.d("SplashScreen", currentTheme.toString())
    }

    var currentLanguage by remember {
        mutableStateOf(
            when (getSavedLanguageCode(context)) {
                "en" -> AppLanguage.ENGLISH
                else -> AppLanguage.KOREAN // 저장된 값이 없거나 "ko"이면 한국어
            }
        )
    }

    // currentLanguage 상태 변경 시 SharedPreferences에 저장 (새로 추가된 부분)
    LaunchedEffect(currentLanguage) {
        saveLanguageCode(context, currentLanguage.code)
        Log.d("SplashScreen", "Language saved: ${currentLanguage.code}")
    }
    val uiColor = getUiColorForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조
    val oppositeUiColor = getOppositeUiColorForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조
    val smallImage = getSmallImageForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조
    val mediumImage = getMediumImageForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조    val context = LocalContext.current
    val largeImage = getLargeImageForTheme(LocalAppTheme.current) // 현재 테마 전달 또는 함수 내부에서 참조

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent, // 배경 투명 설정
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent, // TopAppBar 배경 투명
                ),
                title = {
                    // Splash 화면의 타이틀 텍스트 (필요시 여기에 추가)
                    // Text("Splash Screen", color = uiColor)
                },
                actions = {
                    // 설정 아이콘 버튼
                    IconButton(onClick = {
                        onSettingsClick()
                    }) {
                        // 설정 아이콘
                        Image(
                            painter = painterResource(id = R.drawable.ic_setting), // 설정 아이콘 리소스
                            contentDescription = "설정", // 접근성 설명
                            colorFilter = ColorFilter.tint(uiColor),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clickable { // Box 전체에 클릭 가능 모디파이어
                    onBoxClick() // 클릭 시 전달받은 람다 함수 호출
                }
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(id = currentLanguage.getSplashTitleResourceId()),
                    color = uiColor,
                    style = Typography.displayLarge,
                    modifier = Modifier
                        .padding(start = 30.dp, top = 80.dp)
                )
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(id = largeImage),
                    contentDescription = "캐릭터 이미지",
                    modifier = Modifier
                        .size(341.dp, 400.dp)
                        .align(Alignment.End)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_language), // 변경
                    contentDescription = "언어 아이콘",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(uiColor)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = AppLanguage.ENGLISH.getLanguageTextResourceId()), // 항상 English 텍스트 표시
                    color = uiColor, // <- uiColor 적용
                    style = Typography.displaySmall,
                    modifier = Modifier.clickable {
                        currentLanguage = AppLanguage.ENGLISH // 언어 상태만 변경
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = AppLanguage.KOREAN.getLanguageTextResourceId()), // 항상 한국어 텍스트 표시
                    color = uiColor, // <- uiColor 적용
                    style = Typography.displaySmall,
                    modifier = Modifier.clickable {
                        currentLanguage = AppLanguage.KOREAN // 언어 상태만 변경
                    }
                )
            }
        }
    }
}
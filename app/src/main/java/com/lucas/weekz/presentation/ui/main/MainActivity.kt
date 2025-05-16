package com.lucas.weekz.presentation.ui.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lucas.weekz.presentation.theme.AppTheme
import com.lucas.weekz.presentation.theme.LocalAppTheme
import com.lucas.weekz.presentation.theme.ThemedApp
import com.lucas.weekz.presentation.ui.schedule.AddScheduleScreen
import com.lucas.weekz.presentation.ui.schedule.CompleteScheduleScreen
import com.lucas.weekz.presentation.ui.schedule.EditScheduleScreen
import com.lucas.weekz.presentation.ui.schedule.FailedScheduleScreen
import com.lucas.weekz.presentation.ui.schedule.ScheduleViewModel
import com.lucas.weekz.presentation.ui.schedule.SelectScheduleScreen
import com.lucas.weekz.presentation.ui.setting.SettingThemeScreen
import com.lucas.weekz.presentation.ui.sign.EXTRA_DESTINATION_ROUTE
import com.lucas.weekz.presentation.utill.composableActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

// SharedPreferences 키 정의
const val PREFS_NAME = "theme_prefs"
const val KEY_SELECTED_THEME = "selected_theme"

// AppTheme enum을 String으로 변환 및 복원하는 헬퍼 함수
fun AppTheme.toName(): String = this.name
fun String.toAppTheme(): AppTheme? = try { AppTheme.valueOf(this) } catch (e: IllegalArgumentException) { null }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val sharedPreferences = remember { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

            // 시스템 기본 테마 결정 (저장된 테마가 없을 경우 사용)
            val systemIsDark = isSystemInDarkTheme()
            val defaultSystemTheme = if (systemIsDark) AppTheme.DARK1 else AppTheme.LIGHT1

            // SharedPreferences에서 저장된 테마 불러오기, 없으면 시스템 기본 테마 사용
            var currentAppTheme by remember {
                mutableStateOf(
                    sharedPreferences.getString(KEY_SELECTED_THEME, null)?.toAppTheme() ?: defaultSystemTheme
                )
            }
            // CompositionLocalProvider는 LocalAppTheme.current를 업데이트하여 하위 Composable에서 접근 가능하게 함
            CompositionLocalProvider(LocalAppTheme provides currentAppTheme) {
                ThemedApp(currentAppTheme = currentAppTheme) {
                    val startDestination = intent.getStringExtra(EXTRA_DESTINATION_ROUTE)
                    val initialRoute = startDestination ?: Screen.MainScreen.route
                    val navController = rememberNavController()
                    val scheduleViewModel: ScheduleViewModel = composableActivityViewModel()

                    NavHost(navController = navController, startDestination = initialRoute) {
                        composable(Screen.MainScreen.route) {
                            MainScreen(navController = navController)
                        }
                        composable(Screen.AddScheduleScreen.route) {
                            AddScheduleScreen(navController = navController, viewModel = scheduleViewModel)
                        }
                        composable(Screen.SelectScheduleScreen.route) {
                            SelectScheduleScreen(navController = navController, viewModel = scheduleViewModel)
                        }
                        composable(Screen.EditScheduleScreen.route) {
                            EditScheduleScreen(navController = navController, viewModel = scheduleViewModel)
                        }
                        composable(Screen.CompleteScheduleScreen.route) {
                            CompleteScheduleScreen(navController = navController, viewModel = scheduleViewModel)
                        }
                        composable(
                            route = Screen.FailedScheduleScreen.route + "?message={message}", // 메시지를 전달받을 수 있도록
                            arguments = listOf(navArgument("message") { nullable = true; defaultValue = null })
                        ) { backStackEntry ->
                            val message = backStackEntry.arguments?.getString("message")
                            FailedScheduleScreen(navController = navController, message = message)
                        }
                        composable(Screen.SettingThemeScreen.route) {
                            SettingThemeScreen(
                                navController = navController,
                                onThemeSelected = { selectedTheme ->
                                    currentAppTheme = selectedTheme // 상태 업데이트
                                    // SharedPreferences에 선택된 테마 저장
                                    sharedPreferences.edit().putString(KEY_SELECTED_THEME, selectedTheme.toName()).apply()
                                }
                            )
                        }
                    }

                    if (startDestination != null) {
                        navController.navigate(startDestination) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
}
sealed class Screen(val route: String){
    object MainScreen : Screen("main")
    object AddScheduleScreen : Screen("add")
    object SelectScheduleScreen : Screen("select")
    object EditScheduleScreen : Screen("edit")
    object CompleteScheduleScreen : Screen("complete")
    object FailedScheduleScreen : Screen("failed")
    object SettingThemeScreen : Screen("settingTheme")
}
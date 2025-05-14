package com.lucas.weekz.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lucas.weekz.presentation.Utill.composableActivityViewModel
import com.lucas.weekz.presentation.theme.ThemedApp
import com.lucas.weekz.presentation.ui.schedule.AddScheduleScreen
import com.lucas.weekz.presentation.ui.schedule.CompleteScheduleScreen
import com.lucas.weekz.presentation.ui.schedule.EditScheduleScreen
import com.lucas.weekz.presentation.ui.schedule.ScheduleViewModel
import com.lucas.weekz.presentation.ui.schedule.SelectScheduleScreen
import com.lucas.weekz.presentation.ui.setting.SettingThemeScreen
import com.lucas.weekz.presentation.ui.setting.SettingViewModel
import com.lucas.weekz.presentation.ui.sign.EXTRA_DESTINATION_ROUTE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            ThemedApp{
                // SplashActivity에서 전달된 목적지 경로 확인
                val startDestination = intent.getStringExtra(EXTRA_DESTINATION_ROUTE)

                // 시작 목적지 설정: 전달된 경로가 있으면 해당 경로, 없으면 메인 화면 경로
                val initialRoute = startDestination ?: Screen.MainScreen.route

                val navController = rememberNavController()
                val scheduleViewModel: ScheduleViewModel = composableActivityViewModel()

                NavHost(navController = navController, startDestination = initialRoute){ // 시작 목적지 설정
                    composable(Screen.MainScreen.route){
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
                    composable(Screen.CompleteScheduleScreen.route){
                        CompleteScheduleScreen(navController, viewModel = scheduleViewModel)
                    }
                    composable(Screen.SettingThemeScreen.route){
                        // SettingThemeScreen 호출 시 onThemeSelected 람다 연결
                        SettingThemeScreen(
                            navController = navController,
                            onThemeSelected = { isDark ->
                                SettingViewModel().setTheme(isDark)
                            }
                        )
                    }
                }

                // 만약 SplashActivity에서 특정 경로로 이동하라는 요청이 있었으면 해당 경로로 Navigation 수행
                // 이 로직은 NavHost가 설정된 후에 실행되어야 합니다.
                if (startDestination != null) {
                    // LaunchedEffect 등을 사용하여 첫 프레임 이후에 Navigation을 수행하는 것이 안정적일 수 있습니다.
                    // 여기서는 간단히 NavController가 준비되었을 때 바로 이동을 시도합니다.
                    // 필요에 따라 LaunchedEffect로 감싸서 비동기적으로 처리할 수 있습니다.
                    navController.navigate(startDestination) {
                        // SettingThemeScreen으로 이동한 후 백 스택에서 MainScreen 또는 이전 화면들을 제거할지 결정합니다.
                        // 여기서는 MainScreen을 포함한 이전 화면들을 제거하도록 설정합니다.
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true // 이미 스택에 동일한 목적지가 있다면 재사용
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
    object SettingThemeScreen : Screen("settingTheme")
}

@Composable
fun MyApp(){
    val navController = rememberNavController()
    val viewModel: ScheduleViewModel = composableActivityViewModel()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(Screen.MainScreen.route){
            MainScreen(navController = navController)
        }
        composable(Screen.AddScheduleScreen.route) {
            AddScheduleScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.SelectScheduleScreen.route) {
            SelectScheduleScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.EditScheduleScreen.route) {
            EditScheduleScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.CompleteScheduleScreen.route){
            CompleteScheduleScreen(navController, viewModel = viewModel)
        }
        composable(Screen.SettingThemeScreen.route){
            // SettingThemeScreen 호출 시 onThemeSelected 람다 연결
            SettingThemeScreen(
                navController = navController,
                onThemeSelected = { isDark ->
                    SettingViewModel().setTheme(isDark)
                }
            )
        }
    }
}
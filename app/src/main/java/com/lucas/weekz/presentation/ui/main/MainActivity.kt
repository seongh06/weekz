package com.lucas.weekz.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lucas.weekz.presentation.theme.ThemedApp
import com.lucas.weekz.presentation.ui.schedule.AddScheduleScreen
import com.lucas.weekz.presentation.ui.schedule.SelectScheduleScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            ThemedApp{
                MyApp()
            }
        }
    }
}

sealed class Screen(val route: String){
    object MainScreen : Screen("main")
    object AddScheduleScreen : Screen("add")
    object SelectScheduleScreen : Screen("select")
}

@Composable
fun MyApp(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(Screen.MainScreen.route){
            MainScreen(navController = navController)
        }
        composable(route = Screen.AddScheduleScreen.route) {
            AddScheduleScreen(navController = navController)
        }
        composable(route = Screen.SelectScheduleScreen.route) {
            SelectScheduleScreen(navController = navController)
        }
    }
}
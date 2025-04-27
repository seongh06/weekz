package com.lucas.weekz.presentation.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController){
    navController.navigate(Screen.AddScheduleScreen.route)
}
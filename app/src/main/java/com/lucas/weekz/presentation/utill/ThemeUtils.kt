package com.lucas.weekz.presentation.utill

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.lucas.weekz.R
import com.lucas.weekz.presentation.theme.AppTheme
import com.lucas.weekz.presentation.theme.Black
import com.lucas.weekz.presentation.theme.LocalAppTheme
import com.lucas.weekz.presentation.theme.White

@Composable
    fun getUiColorForTheme(appTheme: AppTheme?): Color {
        val current = appTheme ?: LocalAppTheme.current ?: AppTheme.LIGHT1 // 안전하게 현재 테마 가져오기
        return when (current) {
            AppTheme.DARK1, AppTheme.DARK2, AppTheme.DARK3, AppTheme.DARK4, AppTheme.DARK5 -> Color.White
            else -> Black // AppTheme.LIGHT1, LIGHT2, LIGHT3, LIGHT4 및 기타 경우
        }
    }

@Composable
fun getOppositeUiColorForTheme(appTheme: AppTheme?): Color {
    val current = appTheme ?: LocalAppTheme.current ?: AppTheme.LIGHT1 // 안전하게 현재 테마 가져오기
    return when (current) {
        AppTheme.DARK1, AppTheme.DARK2, AppTheme.DARK3, AppTheme.DARK4, AppTheme.DARK5 -> Color.Black
        else -> White // AppTheme.LIGHT1, LIGHT2, LIGHT3, LIGHT4 및 기타 경우
    }
}

    @Composable
    fun getSmallImageForTheme(appTheme: AppTheme?): Int {
        val current = appTheme ?: LocalAppTheme.current ?: AppTheme.LIGHT1 // 안전하게 현재 테마 가져오기
        return when (current) {
            AppTheme.DARK1, AppTheme.DARK2, AppTheme.DARK3, AppTheme.DARK4, AppTheme.DARK5 -> R.drawable.img_small_black_1
            else -> R.drawable.img_small_white_1 // AppTheme.LIGHT1, LIGHT2, LIGHT3, LIGHT4 및 기타 경우
        }
    }

    @Composable
    fun getMediumImageForTheme(appTheme: AppTheme?): Int {
        val current = appTheme ?: LocalAppTheme.current ?: AppTheme.LIGHT1
        return when (current) {
            AppTheme.DARK1, AppTheme.DARK2, AppTheme.DARK3, AppTheme.DARK4, AppTheme.DARK5 -> R.drawable.img_medium_black_1
            else -> R.drawable.img_medium_white_1
        }
    }

@Composable
fun getLargeImageForTheme(appTheme: AppTheme?): Int{
    val current = appTheme ?: LocalAppTheme.current ?: AppTheme.LIGHT1
    return when(current){
        AppTheme.DARK1, AppTheme.DARK2, AppTheme.DARK3, AppTheme.DARK4, AppTheme.DARK5 -> R.drawable.img_large_black
        else -> R.drawable.img_large_white
    }
}
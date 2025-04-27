package com.lucas.weekz.presentation.component.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lucas.weekz.R
import java.lang.reflect.Modifier

enum class AppBarMenu(
    val horizontalPadding: Dp,
    val shape: Shape = RectangleShape, // 사각형 모양
    @DrawableRes val icon: Int,
    @StringRes val contentDescription: Int,
) {
    BACK(
        horizontalPadding = 20.dp,
        icon = R.drawable.ic_arrow_back,
        contentDescription = R.string.back_description,
    )

}

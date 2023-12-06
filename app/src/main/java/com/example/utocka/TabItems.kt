package com.example.utocka

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.ui.graphics.vector.ImageVector

data class TabItem(
    val title: String, val unselectedIcon: ImageVector, val selectedIcon: ImageVector
)

val tabItems = listOf(
    TabItem(
        title = "Отправка данных",
        unselectedIcon = Icons.Outlined.ArrowUpward,
        selectedIcon = Icons.Filled.ArrowUpward
    ), TabItem(
        title = "Статистика",
        unselectedIcon = Icons.Outlined.ArrowDownward,
        selectedIcon = Icons.Filled.ArrowDownward
    )
)



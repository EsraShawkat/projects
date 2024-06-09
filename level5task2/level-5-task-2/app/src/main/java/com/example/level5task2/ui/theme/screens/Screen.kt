package com.example.level5task2.ui.theme.screens

import androidx.annotation.StringRes
import com.example.level5task2.R

sealed class Screen(
    val route: String,
    @StringRes val button: Int,
    val icon: Int
)
{
    object PlayScreen: Screen("play_screen", R.string.play, R.drawable.baseline_games_24)
    object HistoryScreen: Screen("history_screen", R.string.history, R.drawable.outline_browse_gallery_24)
}

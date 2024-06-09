package com.example.level4task2.ui.theme.screens

/**
 * Screen metadata for Pets app.
 */

sealed class MovieScreens(
    val route: String,

    ) {
    object SearchScreen :
        MovieScreens("search")

    object InfoScreen :
        MovieScreens("info")
}
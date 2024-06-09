package com.example.level4task2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level4task2.data.model.Movie
import com.example.level4task2.ui.theme.Level4Task2Theme
import com.example.level4task2.ui.theme.screens.InfoScreen
import com.example.level4task2.ui.theme.screens.MovieScreens
import com.example.level4task2.ui.theme.screens.SearchScreen
import com.example.level4task2.viewmodel.MovieViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Level4Task2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MovieNavHost(navController = navController, innerPadding = PaddingValues())
                }
            }
        }
    }
}


@Composable
private fun MovieNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    val viewModel: MovieViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = MovieScreens.SearchScreen.route,
        Modifier.padding(innerPadding)
    ) {
        composable(MovieScreens.SearchScreen.route) { SearchScreen(viewModel, navController) }
        composable(MovieScreens.InfoScreen.route) { InfoScreen(viewModel) }
    }
}
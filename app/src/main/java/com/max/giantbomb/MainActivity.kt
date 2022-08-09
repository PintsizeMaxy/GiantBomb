package com.max.giantbomb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.max.giantbomb.details.GameDetailsScreen
import com.max.giantbomb.history.GameHistoryScreen
import com.max.giantbomb.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "search") {
                    composable("search") {
                        SearchScreen(
                            hiltViewModel(),
                            { navController.navigate("game_details/${it}") },
                            { navController.navigate("history") })
                    }
                    composable(
                        "game_details/{gameId}",
                        arguments = listOf(navArgument("gameId") { type = NavType.StringType })
                    ) {
                        GameDetailsScreen(hiltViewModel()) {
                            navController.popBackStack()
                        }
                    }
                    composable("history") {
                        GameHistoryScreen(
                            viewModel = hiltViewModel(),
                            onBack = { navController.popBackStack() },
                            navigateToDetails = { navController.navigate("game_details/${it}") }
                        )
                    }
                }
            }
        }
    }
}
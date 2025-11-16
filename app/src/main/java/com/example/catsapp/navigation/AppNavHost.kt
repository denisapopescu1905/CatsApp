package com.example.catsapp.navigation

import CatDetailScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catsapp.ConnectivityViewModel
import com.example.catsapp.screens.CatListScreen
import com.example.catsapp.network.CatImage

/**
 * AppNavHost is the main navigation host for the CatsApp application.
 * It defines the navigation graph and handles routing between screens.
 *
 * @param cats List of [CatImage] objects to display in the list and detail screens.
 * @param connectivityViewModel ViewModel that observes network connectivity changes.
 * @param onRefresh Lambda function invoked when a swipe-to-refresh action is triggered.
 */
@Composable
fun AppNavHost(
    cats: List<CatImage>,
    connectivityViewModel: ConnectivityViewModel,
    onRefresh: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {

        composable("list") {
            CatListScreen(
                cats = cats,
                isRefreshing = false,
                connectivityViewModel = connectivityViewModel,
                onRefresh = onRefresh,
                onCatClick = { cat ->
                    navController.navigate("detail/${cat.id}")
                }
            )
        }

        composable("detail/{catId}") { backStackEntry ->
            val catId = backStackEntry.arguments?.getString("catId")
            val cat = cats.firstOrNull { it.id == catId }
            if (cat != null) {
                CatDetailScreen(cat)
            }
        }
    }
}
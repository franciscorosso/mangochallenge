package com.example.mangochallenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mangochallenge.feature.favorites.FavoritesScreen
import com.example.mangochallenge.feature.products.ProductsScreen
import com.example.mangochallenge.feature.profile.ProfileScreen

@Composable
fun MangoNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Products.route,
        modifier = modifier
    ) {
        composable(Screen.Products.route) {
            ProductsScreen()
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
    }
}

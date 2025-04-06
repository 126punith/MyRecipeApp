package com.example.myrecipeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RecipeApp(navController: NavHostController) {
    val recipeViewModel: MainViewModel = viewModel()
    val viewstate by recipeViewModel.categoriesState

    NavHost(
        navController = navController,
        startDestination = Screen.RecipeScreen.route // Use the route string here
    ) {
        composable(route = Screen.RecipeScreen.route) {
            RecipeScreen(
                viewstate = viewstate,
                navigateToDetail = { category ->
                    // Saving the category to the savedStateHandle to pass it to the next screen
                    navController.currentBackStackEntry?.savedStateHandle?.set("Cat", category)
                    navController.navigate(Screen.DetailScreen.route) // Navigating to DetailScreen
                }
            )
        }

        composable(route = Screen.DetailScreen.route) {
            // Retrieving the category from savedStateHandle
            val category = navController.previousBackStackEntry?.savedStateHandle?.get<Category>("Cat")
                ?: Category("", "", "", "") // Fallback to empty category if not found

            CategoryDetailsScreen(category = category)
        }
    }
}

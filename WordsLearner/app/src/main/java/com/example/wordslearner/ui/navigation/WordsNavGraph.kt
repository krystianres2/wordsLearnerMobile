package com.example.wordslearner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wordslearner.ui.home.HomeDestination
import com.example.wordslearner.ui.home.HomeScreen
import com.example.wordslearner.ui.test.TestDestination
import com.example.wordslearner.ui.test.TestScreen
import com.example.wordslearner.ui.word.*

//Nawigacja
@Composable
fun WordsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToWordEntry = { navController.navigate(WordEntryDestination.route) },
                navigateToWordUpdate = {
                    navController.navigate("${WordDetailsDestination.route}/${it}")
                },
                navigateToTest = {navController.navigate(TestDestination.route)},

                )
        }
        composable(route = WordEntryDestination.route) {
            WordEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = WordDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(WordDetailsDestination.wordIdArg) {
                type = NavType.IntType//ID obiektu typu integer
            })
        ) {
            WordDetailsScreen(
                navigateToEditWord = { navController.navigate("${WordEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = WordEditDestination.routeWithArgs,
            arguments = listOf(navArgument(WordEditDestination.wordIdArg) {
                type = NavType.IntType
            })
        ) {
            WordEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(route = TestDestination.route){
            TestScreen({ navController.navigate(HomeDestination.route) })
        }
    }
}
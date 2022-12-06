package net.ucoz.abondarenko.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.ucoz.abondarenko.MainViewModel
import net.ucoz.abondarenko.screen.*


@Composable
fun NoteNavHost(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Start.route) {
        composable(Screen.Start.route) {
            StartScreen(navController, viewModel)
        }
        composable(Screen.Main.route) {
            MainScreen(navController, viewModel)
        }
        composable(Screen.Add.route) {
            AddScreen(navController, viewModel)
        }
        composable(Screen.Note.route) {
            NoteScreen(navController, viewModel)
        }
    }
}
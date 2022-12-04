package net.ucoz.abondarenko.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.ucoz.abondarenko.screen.*


@Composable
fun NoteNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Start.route) {
        composable(Screen.Start.route) {
            StartScreen(navController)
        }
        composable(Screen.Main.route) {
            MainScreen(navController)
        }
        composable(Screen.Add.route) {
            AddScreen(navController)
        }
        composable(Screen.Note.route) {
            NoteScreen(navController)
        }
    }
}
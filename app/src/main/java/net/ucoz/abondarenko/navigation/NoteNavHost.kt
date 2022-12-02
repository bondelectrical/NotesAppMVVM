package net.ucoz.abondarenko.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.ucoz.abondarenko.screen.Add
import net.ucoz.abondarenko.screen.Main
import net.ucoz.abondarenko.screen.Note
import net.ucoz.abondarenko.screen.Start


@Composable
fun NoteNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Start.route) {
        composable(Screen.Start.route) {
            Start(navController)
        }
        composable(Screen.Main.route) {
            Main(navController)
        }
        composable(Screen.Add.route) {
            Add(navController)
        }
        composable(Screen.Note.route) {
            Note(navController)
        }
    }
}
package net.ucoz.abondarenko.navigation

sealed class Screen(val route: String ) {
    object Start: Screen("start_screen")
    object Main: Screen("main_screen")
    object Add: Screen("add_screen")
    object Note: Screen("note_screen")
}
package net.ucoz.abondarenko.navigation

import net.ucoz.abondarenko.utils.Constants.Screen.ADD_SCREEN
import net.ucoz.abondarenko.utils.Constants.Screen.MAIN_SCREEN
import net.ucoz.abondarenko.utils.Constants.Screen.NOTE_SCREEN
import net.ucoz.abondarenko.utils.Constants.Screen.START_SCREEN

sealed class Screen(val route: String ) {
    object Start: Screen(START_SCREEN)
    object Main: Screen(MAIN_SCREEN)
    object Add: Screen(ADD_SCREEN)
    object Note: Screen(NOTE_SCREEN)
}
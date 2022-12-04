package net.ucoz.abondarenko.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import net.ucoz.abondarenko.navigation.Screen
import net.ucoz.abondarenko.ui.theme.NotesAppTheme

@Composable
fun StartScreen(navHostController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "What will we use?")
            Spacer(modifier = Modifier.size(32.dp))
            Button(
                onClick = { navHostController.navigate(route = Screen.Main.route) },
                modifier = Modifier
                    .height(48.dp)
                    .width(250.dp),
                shape = RoundedCornerShape(32.dp),
                elevation = ButtonDefaults.elevation(8.dp),
            ) {
                Text(text = "Room database")
            }
            Spacer(modifier = Modifier.size(32.dp))
            Button(
                onClick = { navHostController.navigate(route = Screen.Main.route) },
                modifier = Modifier
                    .height(48.dp)
                    .width(250.dp),
                shape = RoundedCornerShape(32.dp),
                elevation = ButtonDefaults.elevation(8.dp),
            ) {
                Text(text = "Firebase database")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun previewStartScreen() {
    NotesAppTheme() {
        StartScreen(navHostController = rememberNavController())
    }
}
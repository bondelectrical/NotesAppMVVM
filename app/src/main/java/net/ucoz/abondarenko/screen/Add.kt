package net.ucoz.abondarenko.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import net.ucoz.abondarenko.navigation.Screen
import net.ucoz.abondarenko.ui.theme.NotesAppTheme


@Composable
fun AddScreen(navHostController: NavHostController) {
    var title by remember { mutableStateOf("") }
    var subTitle by remember { mutableStateOf("") }
    Scaffold() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Add new note",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                label = { Text(text = "Note title") }
            )
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = subTitle,
                onValueChange = {
                    subTitle = it
                },
                label = { Text(text = "Note subtitle") }
            )
            Spacer(modifier = Modifier.size(32.dp))
            Button(
                onClick = { navHostController.navigate(route = Screen.Main.route) },
                modifier = Modifier
                    .height(48.dp)
                    .width(250.dp),
                shape = RoundedCornerShape(32.dp),
                elevation = ButtonDefaults.elevation(8.dp),
            ) {
                Text(text = "Add note")
            }
        }

    }

}


@Preview(showBackground = true)
@Composable
fun previewAddScreen() {
    NotesAppTheme() {
        AddScreen(navHostController = rememberNavController())
    }
}
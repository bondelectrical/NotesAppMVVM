package net.ucoz.abondarenko.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import net.ucoz.abondarenko.navigation.Screen
import net.ucoz.abondarenko.ui.theme.NotesAppTheme

@Composable
fun MainScreen(navHostController: NavHostController) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navHostController.navigate(route = Screen.Add.route)
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add ",
                    tint = Color.White
                )
            }

        }) {
        Column() {
            NoteItem(title = "Note 1", subtitle = "Subtitle for Note 1", navHostController = navHostController)
            NoteItem(title = "Note 2", subtitle = "Subtitle for Note 2", navHostController = navHostController)
            NoteItem(title = "Note 3", subtitle = "Subtitle for Note 3", navHostController = navHostController)
            NoteItem(title = "Note 4", subtitle = "Subtitle for Note 4", navHostController = navHostController)

        }

    }

}


@Composable
fun NoteItem(title: String, subtitle: String, navHostController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp)
            .clickable { navHostController.navigate(route = Screen.Note.route) },
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = subtitle)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewMainScreen() {
    NotesAppTheme() {
        MainScreen(navHostController = rememberNavController())
    }
}
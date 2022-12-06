package net.ucoz.abondarenko.screen

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import net.ucoz.abondarenko.MainViewModel
import net.ucoz.abondarenko.MainViewModelFactory
import net.ucoz.abondarenko.model.Note
import net.ucoz.abondarenko.navigation.Screen
import net.ucoz.abondarenko.ui.theme.NotesAppTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddScreen(navHostController: NavHostController, viewModel: MainViewModel) {
    var title by remember { mutableStateOf("") }
    var subTitle by remember { mutableStateOf("") }
    var isButtonEnable by remember {
        mutableStateOf(false)
    }
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
                    isButtonEnable = title.isNotEmpty() && subTitle.isNotEmpty()
                    title = it
                },
                label = { Text(text = "Note title") },
                isError = title.isEmpty()
            )
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = subTitle,
                onValueChange = {
                    isButtonEnable = title.isNotEmpty() && subTitle.isNotEmpty()
                    subTitle = it
                },
                label = { Text(text = "Note subtitle") },
                isError = subTitle.isEmpty()
            )
            Spacer(modifier = Modifier.size(32.dp))
            Button(
                enabled = isButtonEnable,
                onClick = {
                    viewModel.addNote(note = Note(title = title, subtitle = subTitle)) {
                        navHostController.navigate(route = Screen.Main.route)
                    }

                },
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
        val context = LocalContext.current
        val viewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(application = context.applicationContext as Application))
        AddScreen(navHostController = rememberNavController(), viewModel = viewModel)
    }
}
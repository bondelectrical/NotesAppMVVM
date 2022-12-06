package net.ucoz.abondarenko.screen

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import net.ucoz.abondarenko.MainViewModel
import net.ucoz.abondarenko.MainViewModelFactory
import net.ucoz.abondarenko.navigation.Screen
import net.ucoz.abondarenko.ui.theme.NotesAppTheme
import net.ucoz.abondarenko.utils.TYPE_FIREBASE
import net.ucoz.abondarenko.utils.TYPE_ROOM

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StartScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val viewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(application = context.applicationContext as Application))

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "What will we use?")
            Spacer(modifier = Modifier.size(32.dp))
            Button(
                onClick = {
                    viewModel.initDatabase(type = TYPE_ROOM) {
                        navHostController.navigate(route = Screen.Main.route)
                    }

                },
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
                onClick = {
                    viewModel.initDatabase(type = TYPE_FIREBASE) {
                        navHostController.navigate(route = Screen.Main.route)
                    }

                },
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
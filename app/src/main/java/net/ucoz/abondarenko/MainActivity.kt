package net.ucoz.abondarenko

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.ucoz.abondarenko.navigation.NoteNavHost
import net.ucoz.abondarenko.ui.theme.NotesAppTheme
import net.ucoz.abondarenko.ui.theme.Purple700

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                val context = LocalContext.current
                val viewModel: MainViewModel =
                    viewModel(factory = MainViewModelFactory(application = context.applicationContext as Application))
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Notes App") },
                            backgroundColor = Purple700,
                            contentColor = Color.White,
                            elevation = 8.dp
                        )
                    },
                    content = {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            NoteNavHost(viewModel)
                        }

                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotesAppTheme {
    }
}
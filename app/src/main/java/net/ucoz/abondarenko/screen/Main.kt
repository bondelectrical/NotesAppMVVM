package net.ucoz.abondarenko.screen

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import net.ucoz.abondarenko.MainViewModel
import net.ucoz.abondarenko.MainViewModelFactory
import net.ucoz.abondarenko.model.Note
import net.ucoz.abondarenko.navigation.Screen
import net.ucoz.abondarenko.ui.theme.NotesAppTheme
import net.ucoz.abondarenko.ui.theme.Purple700
import net.ucoz.abondarenko.utils.*
import net.ucoz.abondarenko.utils.Constants.Keys.EMPTY

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navHostController: NavHostController, viewModel: MainViewModel) {

    val notes = viewModel.readAllNotes().observeAsState(listOf()).value
    val listState = rememberLazyListState()


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
        LazyColumnScrollbar(
            listState
        ) {

            LazyColumn(state = listState) {
                items(notes) { note ->
                    NoteItem(note = note, navHostController = navHostController)
                    NoteItem(note = note, navHostController = navHostController)
                }
            }
        }


    }

}

//Test_1


@Composable
fun NoteItem(note: Note, navHostController: NavHostController) {

    val noteId = when (DB_TYPE.value) {
        TYPE_FIREBASE -> note.firebaseId
        TYPE_ROOM -> note.id
        else -> EMPTY
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            .clickable { navHostController.navigate(route = Screen.Note.route + "/${noteId}") },
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = note.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = note.subtitle)
        }
    }
}




@Preview(showBackground = true)
@Composable
fun previewMainScreen() {
    NotesAppTheme() {
        val context = LocalContext.current
        val viewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(application = context.applicationContext as Application))
        MainScreen(navHostController = rememberNavController(), viewModel = viewModel)
    }
}
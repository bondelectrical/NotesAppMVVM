package net.ucoz.abondarenko.screen

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import kotlinx.coroutines.launch
import net.ucoz.abondarenko.MainViewModel
import net.ucoz.abondarenko.MainViewModelFactory
import net.ucoz.abondarenko.model.Note
import net.ucoz.abondarenko.navigation.Screen
import net.ucoz.abondarenko.ui.theme.NotesAppTheme
import net.ucoz.abondarenko.utils.Constants
import net.ucoz.abondarenko.utils.Constants.Keys.DELETE
import net.ucoz.abondarenko.utils.Constants.Keys.EDIT_NOTE
import net.ucoz.abondarenko.utils.Constants.Keys.NAV_BACK
import net.ucoz.abondarenko.utils.Constants.Keys.NONE
import net.ucoz.abondarenko.utils.Constants.Keys.UPDATE
import net.ucoz.abondarenko.utils.Constants.Keys.UPDATE_NOTE
import net.ucoz.abondarenko.utils.DB_TYPE
import net.ucoz.abondarenko.utils.TYPE_FIREBASE
import net.ucoz.abondarenko.utils.TYPE_ROOM

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen(navHostController: NavHostController, viewModel: MainViewModel, noteId: String?) {
    val notes = viewModel.readAllNotes().observeAsState(listOf()).value
    val note = when(DB_TYPE.value) {
        TYPE_ROOM -> notes.firstOrNull { it.id == noteId?.toInt() } ?: Note()
        TYPE_FIREBASE -> notes.firstOrNull { it.firebaseId == noteId } ?: Note()
        else -> Note()
    }


    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
//    val bottomSheetState = rememberBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var subTitle by remember { mutableStateOf("") }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetElevation = 8.dp,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        sheetContent = {
            Surface() {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 32.dp)
                ) {
                    Text(
                        text = EDIT_NOTE,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                        },
                        label = { Text(text = Constants.Keys.TITLE) },
                        isError = title.isEmpty()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        value = subTitle,
                        onValueChange = {
                            subTitle = it
                        },
                        label = { Text(text = Constants.Keys.SUBTITLE) },
                        isError = subTitle.isEmpty()
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            viewModel.updateNote(Note(id = note.id, title = title, subtitle = subTitle, firebaseId = note.firebaseId)) {
                               navHostController.navigate(Screen.Main.route)
                            }

                        }) {
                        Text(text = UPDATE_NOTE)

                    }

                }
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = note.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Spacer(modifier = Modifier.size(32.dp))
                        Text(
                            text = note.subtitle,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                title = note.title
                                subTitle = note.subtitle
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        }) {
                        Text(text = UPDATE)

                    }
                    Button(
                        onClick = {
                            viewModel.deleteNote(note = note) {
                                navHostController.navigate(Screen.Main.route)
                            }
                        }) {
                        Text(text = DELETE)

                    }
                }
                Button(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    onClick = {
                        navHostController.navigate(Screen.Main.route)
                    }) {
                    Text(text = NAV_BACK)

                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun previewNoteScreen() {
    NotesAppTheme() {
        val context = LocalContext.current
        val viewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(application = context.applicationContext as Application))
        NoteScreen(
            navHostController = rememberNavController(),
            viewModel = viewModel,
            noteId = "1"
        )
    }
}
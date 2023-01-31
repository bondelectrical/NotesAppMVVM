package net.ucoz.abondarenko.screen

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.BringIntoViewResponder
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
import net.ucoz.abondarenko.utils.Constants.Keys.ADD_NEW_NOTE
import net.ucoz.abondarenko.utils.Constants.Keys.ADD_NOTE
import net.ucoz.abondarenko.utils.Constants.Keys.NOTE_SUBTITLE
import net.ucoz.abondarenko.utils.Constants.Keys.NOTE_TITLE


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddScreen(navHostController: NavHostController, viewModel: MainViewModel) {
    var title by remember { mutableStateOf("") }
    var subTitle by remember { mutableStateOf("") }
    var isButtonEnable by remember {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = BringIntoViewRequester()

    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = ADD_NEW_NOTE,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 32.dp, start = 8.dp, end = 8.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                modifier = Modifier.onFocusEvent { event ->
                    if(event.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }

                },
                value = title,
                onValueChange = {
                    isButtonEnable = title.isNotEmpty() && subTitle.isNotEmpty()
                    title = it
                },
                label = { Text(text = NOTE_TITLE) },
                isError = title.isEmpty(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                modifier = Modifier.onFocusEvent { event ->
                    if(event.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }

                },
                value = subTitle,
                onValueChange = {
                    isButtonEnable = title.isNotEmpty() && subTitle.isNotEmpty()
                    subTitle = it
                },
                label = { Text(text = NOTE_SUBTITLE) },
                isError = subTitle.isEmpty(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
            Spacer(modifier = Modifier
                .size(32.dp)
                .bringIntoViewRequester(bringIntoViewRequester))
            Button(
                enabled = isButtonEnable,
                onClick = {
                    viewModel.addNote(note = Note(title = title, subtitle = subTitle)) {
                        navHostController.navigate(route = Screen.Main.route)
                    }

                },
                modifier = Modifier
                    .height(48.dp)
                    .width(250.dp)
                    ,
                shape = RoundedCornerShape(32.dp),
                elevation = ButtonDefaults.elevation(8.dp),
            ) {
                Text(text = ADD_NOTE)
            }
            Spacer(modifier = Modifier.size(32.dp))
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
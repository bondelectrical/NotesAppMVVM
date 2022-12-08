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
import kotlinx.coroutines.launch
import net.ucoz.abondarenko.MainViewModel
import net.ucoz.abondarenko.MainViewModelFactory
import net.ucoz.abondarenko.navigation.Screen
import net.ucoz.abondarenko.ui.theme.NotesAppTheme
import net.ucoz.abondarenko.utils.*
import net.ucoz.abondarenko.utils.Constants.Keys.FIREBASE_DATABASE
import net.ucoz.abondarenko.utils.Constants.Keys.ROOM_DATABASE
import net.ucoz.abondarenko.utils.Constants.Keys.WHAT_WILL_WE_USE

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StartScreen(navHostController: NavHostController, viewModel: MainViewModel) {

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
//    val bottomSheetState = rememberBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                        text = Constants.Keys.LOG_IN,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = login,
                        onValueChange = {
                            login = it
                        },
                        label = { Text(text = Constants.Keys.LOGIN_TEXT) },
                        isError = login.isEmpty()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        label = { Text(text = Constants.Keys.PASSWORD_TEXT) },
                        isError = password.isEmpty()
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            LOGIN = login
                            PASSWORD = password
                            viewModel.initDatabase(type = TYPE_FIREBASE) {

                            }
                        },
                        enabled = login.isNotEmpty() && password.isNotEmpty()
                    ) {
                        Text(text = Constants.Keys.SIGN_IN)
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
                Text(text = WHAT_WILL_WE_USE)
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
                    Text(text = ROOM_DATABASE)
                }
                Spacer(modifier = Modifier.size(32.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
//                        viewModel.initDatabase(type = TYPE_FIREBASE) {
//                            navHostController.navigate(route = Screen.Main.route)
//                        }

                    },
                    modifier = Modifier
                        .height(48.dp)
                        .width(250.dp),
                    shape = RoundedCornerShape(32.dp),
                    elevation = ButtonDefaults.elevation(8.dp),
                ) {
                    Text(text = FIREBASE_DATABASE)
                }
            }

        }
    }


}

@Preview(showBackground = true)
@Composable
fun previewStartScreen() {
    NotesAppTheme() {
        val context = LocalContext.current
        val viewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(application = context.applicationContext as Application))
        StartScreen(navHostController = rememberNavController(), viewModel = viewModel)
    }
}
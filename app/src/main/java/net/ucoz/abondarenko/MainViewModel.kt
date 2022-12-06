package net.ucoz.abondarenko

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import net.ucoz.abondarenko.model.Note
import net.ucoz.abondarenko.utils.TYPE_FIREBASE
import net.ucoz.abondarenko.utils.TYPE_ROOM

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val readNote: MutableLiveData<List<Note>> by lazy { MutableLiveData<List<Note>>() }
    val dbType: MutableLiveData<String> by lazy { MutableLiveData<String>(TYPE_ROOM) }

    init {
        readNote.value =
            when (dbType.value) {
                TYPE_ROOM -> {
                    listOf<Note>(
                        Note(id = 0, title = "Note 1", subtitle = "Subtitle for Note 1"),
                        Note(id = 1, title = "Note 2", subtitle = "Subtitle for Note 2"),
                        Note(id = 2, title = "Note 3", subtitle = "Subtitle for Note 3"),
                        Note(id = 3, title = "Note 4", subtitle = "Subtitle for Note 4")
                    )
                }
                TYPE_FIREBASE -> {
                    listOf<Note>(
                        Note(id = 0, title = "Note 1", subtitle = "Subtitle for Note 1"),
                        Note(id = 1, title = "Note 2", subtitle = "Subtitle for Note 2"),
                        Note(id = 2, title = "Note 3", subtitle = "Subtitle for Note 3"),
                        Note(id = 3, title = "Note 4", subtitle = "Subtitle for Note 4")
                    )
                }
                else -> {
                    listOf<Note>()
                }
            }
    }

    fun initDatabase(type: String) {
        dbType.value = type

    }
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
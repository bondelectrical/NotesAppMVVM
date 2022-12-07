package net.ucoz.abondarenko

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.ucoz.abondarenko.database.AppRoomDatabase
import net.ucoz.abondarenko.database.room.repository.RoomRepository
import net.ucoz.abondarenko.model.Note
import net.ucoz.abondarenko.utils.REPOSITORY
import net.ucoz.abondarenko.utils.TYPE_FIREBASE
import net.ucoz.abondarenko.utils.TYPE_ROOM

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val context = application

    fun initDatabase(type: String, onSuccess: () -> Unit) {
        when (type) {
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(context = context).getRoomDao()
                REPOSITORY = RoomRepository(dao)
                onSuccess()
            }
            TYPE_FIREBASE -> {

            }
        }
    }

    fun readAllNotes() = REPOSITORY.readAll

    fun updateNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.update(note = note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun deleteNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.delete(note = note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun addNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(note = note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }

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
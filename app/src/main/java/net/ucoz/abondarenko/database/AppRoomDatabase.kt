package net.ucoz.abondarenko.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.ucoz.abondarenko.database.room.dao.NoteRoomDao
import net.ucoz.abondarenko.model.Note
import net.ucoz.abondarenko.utils.Constants.Keys.NOTE_DATABASE

@Database(entities = [Note::class], version = 1)
abstract class AppRoomDatabase: RoomDatabase() {

    abstract fun getRoomDao(): NoteRoomDao

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase {
            return if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(context, AppRoomDatabase::class.java, NOTE_DATABASE)
                        .build()
                INSTANCE as AppRoomDatabase
            } else {
                INSTANCE as AppRoomDatabase
            }
        }
    }
}
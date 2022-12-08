package net.ucoz.abondarenko.database.firebase

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import net.ucoz.abondarenko.database.DatabaseRepository
import net.ucoz.abondarenko.model.Note
import net.ucoz.abondarenko.utils.Constants.Keys.SUBTITLE
import net.ucoz.abondarenko.utils.Constants.Keys.TITLE
import net.ucoz.abondarenko.utils.FIREBASE_ID
import net.ucoz.abondarenko.utils.LOGIN
import net.ucoz.abondarenko.utils.PASSWORD

class FirebaseRepository: DatabaseRepository {

    private val mAuth = FirebaseAuth.getInstance()

    private val database = Firebase.database.reference.child(mAuth.currentUser?.uid.toString())

    override val readAll: LiveData<List<Note>> = AllNotesLivedata()

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        val noteId = database.push().key.toString()
        val mapNotes = hashMapOf<String, Any>()

        mapNotes[FIREBASE_ID] = noteId
        mapNotes[TITLE] = note.title
        mapNotes[SUBTITLE] = note.subtitle

        database.child(noteId).updateChildren(mapNotes).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {

        }

    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun sigOut() {
        mAuth.signOut()
    }

    override fun connectToDatabase(onSuccess: ()-> Unit, onFail: (String)-> Unit) {
        mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD).addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onFail(it.message.toString())
            }
        }
    }
}
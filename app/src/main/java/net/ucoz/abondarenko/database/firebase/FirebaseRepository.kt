package net.ucoz.abondarenko.database.firebase

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import net.ucoz.abondarenko.database.DatabaseRepository
import net.ucoz.abondarenko.model.Note
import net.ucoz.abondarenko.utils.LOGIN
import net.ucoz.abondarenko.utils.PASSWORD

class FirebaseRepository: DatabaseRepository {

    private val mAuth = FirebaseAuth.getInstance()

    override val readAll: LiveData<List<Note>>
        get() = TODO("Not yet implemented")

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
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
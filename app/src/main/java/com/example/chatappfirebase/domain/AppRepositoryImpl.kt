package com.example.chatappfirebase.domain

import com.example.chatappfirebase.data.UserData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor() {

    private val fireStore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val dbRef = FirebaseDatabase.getInstance().reference

    val errorFlow = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val successLogin = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

//    init {
//        fireStore.collection("chat").addSnapshotListener { value, error ->
//            val data = ArrayList<ChatData>()
////            value?.forEach {
////                data.add(
////                    ChatData(
////                        it.id, it.data.getOrDefault("message", "what's up bro ?!") as String, it.data.getOrDefault("time", "00:00:00") as String
////                    )
////                )
////            }
//
//            messageFlow.tryEmit(data)
//
//            errorFlow.tryEmit(error.toString())
//        }
//    }

    fun registerUser(name: String, email: String, password: String): Flow<Result<Unit>> = callbackFlow {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                addUserToDatabase(name, email, auth.currentUser!!.uid)
            }
            .addOnFailureListener {
                errorFlow.tryEmit(it.message.toString())
            }

        awaitClose()
    }


    fun logIn(email: String, password: String): Flow<Result<Unit>> = callbackFlow {

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //..
            }
            .addOnFailureListener {
                errorFlow.tryEmit(it.message.toString())
            }

        awaitClose()

    }


    private fun addUserToDatabase(name: String, email: String, uid: String) {
        dbRef.child("users").child(uid).setValue(UserData(name, email, uid))
    }

}
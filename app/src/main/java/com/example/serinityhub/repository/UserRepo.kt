package com.example.serinityhub.repository

import com.example.serinityhub.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface UserRepo {

    fun register(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    )

    fun login(
        email: String,
        password: String,
        callback: (Boolean, String) -> Unit
    )

    fun addUserToDatabase(
        userId: String,
        model: UserModel,
        callback: (Boolean, String) -> Unit
    )

    fun getCurrentUser(): FirebaseUser?

    fun logout(callback: (Boolean, String) -> Unit)
}


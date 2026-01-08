package com.example.serinityhub.repository

import com.example.serinityhub.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface UserRepo {

    // ---------- EXISTING (UNCHANGED) ----------

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

    // ---------- ADDED FOR PROFILE FEATURE ----------

    fun getUserProfile(
        userId: String,
        callback: (Boolean, UserModel?, String) -> Unit
    )

    fun updateUserProfile(
        userId: String,
        updatedData: Map<String, Any?>,
        callback: (Boolean, String) -> Unit
    )

    fun deleteUserAccount(
        userId: String,
        callback: (Boolean, String) -> Unit
    )
}

package com.example.serinityhub.viewmodel

import androidx.lifecycle.ViewModel
import com.example.serinityhub.model.UserModel
import com.example.serinityhub.repository.UserRepoImpl

class UserViewModel(private val repo: UserRepoImpl = UserRepoImpl()) : ViewModel() {

    // ---------------- LOGIN ----------------
    fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        repo.login(email, password) { success, msg ->
            callback(success, msg)
        }
    }

    // ---------------- REGISTER ----------------
    fun registerUser(email: String, password: String, dateOfBirth: String, callback: (Boolean, String) -> Unit) {
        repo.register(email, password) { success, msg, userId ->
            if (success) {
                val user = UserModel(
                    userId = userId,
                    email = email,
                    password = password,
                    dob = dateOfBirth
                )
                repo.addUserToDatabase(userId, user) { dbSuccess, dbMsg ->
                    callback(dbSuccess, dbMsg)
                }
            } else {
                callback(false, msg)
            }
        }
    }
}

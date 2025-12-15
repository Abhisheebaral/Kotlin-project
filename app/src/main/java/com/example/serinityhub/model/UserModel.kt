package com.example.serinityhub.model

data class UserModel(
    val userId: String = "",
    val email: String = "",
    val password: String = "",
    val dob: String = "",
    val firstName: String = "",
    val lastName: String = ""
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userId" to userId,
            "email" to email,
            "password" to password,
            "dob" to dob,
            "firstName" to firstName,
            "lastName" to lastName
        )
    }
}

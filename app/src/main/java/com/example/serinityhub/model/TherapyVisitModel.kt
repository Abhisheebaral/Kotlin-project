package com.example.serinityhub.model

data class TherapyVisitModel(
    val visitId: String = "",
    val userId: String = "",
    val name: String = "",
    val therapyType: String = "",
    val visitDate: String = "",
    val location: String = ""
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "visitId" to visitId,
            "userId" to userId,
            "name" to name,
            "therapyType" to therapyType,
            "visitDate" to visitDate,
            "location" to location
        )
    }
}

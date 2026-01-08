package com.example.serinityhub.repository

import com.example.serinityhub.model.TherapyVisitModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TherapyVisitRepoImpl : TherapyVisitRepo {

    private val database = FirebaseDatabase.getInstance().reference.child("therapy_visits")

    override fun addVisit(model: TherapyVisitModel, callback: (Boolean, String) -> Unit) {
        val visitId = if (model.visitId.isEmpty()) database.push().key ?: "" else model.visitId
        val visitWithId = model.copy(visitId = visitId)

        database.child(visitId).setValue(visitWithId)
            .addOnCompleteListener {
                if (it.isSuccessful) callback(true, "Visit added successfully")
                else callback(false, it.exception?.message ?: "Failed to add visit")
            }
    }

    override fun getVisitsByUser(userId: String, callback: (List<TherapyVisitModel>) -> Unit) {
        database.orderByChild("userId").equalTo(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<TherapyVisitModel>()
                    for (child in snapshot.children) {
                        val visit = child.getValue(TherapyVisitModel::class.java)
                        if (visit != null) list.add(visit)
                    }
                    callback(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(emptyList())
                }
            })
    }

    override fun updateVisit(model: TherapyVisitModel, callback: (Boolean, String) -> Unit) {
        if (model.visitId.isEmpty()) {
            callback(false, "Visit ID is empty")
            return
        }
        database.child(model.visitId).setValue(model)
            .addOnCompleteListener {
                if (it.isSuccessful) callback(true, "Visit updated successfully")
                else callback(false, it.exception?.message ?: "Failed to update visit")
            }
    }

    override fun deleteVisit(visitId: String, callback: (Boolean, String) -> Unit) {
        if (visitId.isEmpty()) {
            callback(false, "Visit ID is empty")
            return
        }
        database.child(visitId).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) callback(true, "Visit deleted successfully")
                else callback(false, it.exception?.message ?: "Failed to delete visit")
            }
    }
}

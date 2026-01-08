package com.example.serinityhub.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.serinityhub.model.TherapyVisitModel
import com.example.serinityhub.repository.TherapyVisitRepoImpl
import com.google.firebase.auth.FirebaseAuth

class TherapyVisitViewModel(private val repo: TherapyVisitRepoImpl) : ViewModel() {

    private val currentUserId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    // Compose-friendly list to observe appointments
    private val _appointments = mutableStateListOf<TherapyVisitModel>()
    fun getAllVisitsAsState(): SnapshotStateList<TherapyVisitModel> = _appointments

    // ---------------- Add a visit ----------------
    fun addVisit(model: TherapyVisitModel, callback: (Boolean, String) -> Unit) {
        val visitWithUser = model.copy(userId = currentUserId ?: "")
        repo.addVisit(visitWithUser) { success, msg ->
            callback(success, msg)
            if (success) getAllVisits { } // Refresh the list after adding
        }
    }

    // ---------------- Get all visits of current user ----------------
    fun getAllVisits(callback: (List<TherapyVisitModel>) -> Unit) {
        val uid = currentUserId
        if (uid != null) {
            repo.getVisitsByUser(uid) { list ->
                _appointments.clear()
                _appointments.addAll(list)
                callback(list)
            }
        } else {
            _appointments.clear()
            callback(emptyList())
        }
    }

    // ---------------- Update visit ----------------
    fun updateVisit(visit: TherapyVisitModel, callback: (Boolean, String) -> Unit) {
        repo.updateVisit(visit) { success, msg ->
            callback(success, msg)
            if (success) getAllVisits { } // Refresh after update
        }
    }

    // ---------------- Delete visit ----------------
    fun deleteVisit(visitId: String, callback: (Boolean, String) -> Unit) {
        repo.deleteVisit(visitId) { success, msg ->
            callback(success, msg)
            if (success) getAllVisits { } // Refresh after deletion
        }
    }
}

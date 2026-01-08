package com.example.serinityhub.repository

import com.example.serinityhub.model.TherapyVisitModel

interface TherapyVisitRepo {

    fun addVisit(model: TherapyVisitModel, callback: (Boolean, String) -> Unit)

    fun getVisitsByUser(userId: String, callback: (List<TherapyVisitModel>) -> Unit)

    fun updateVisit(model: TherapyVisitModel, callback: (Boolean, String) -> Unit)

    fun deleteVisit(visitId: String, callback: (Boolean, String) -> Unit)
}


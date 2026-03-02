package com.example.serinityhub

import com.example.serinityhub.model.TherapyVisitModel
import com.example.serinityhub.repository.TherapyVisitRepoImpl
import com.example.serinityhub.viewmodel.TherapyVisitViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.MockedStatic
import org.mockito.Mockito.mockStatic
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class TherapyVisitViewModelTest {

    private lateinit var mockedFirebaseAuth: MockedStatic<FirebaseAuth>
    private val firebaseAuth: FirebaseAuth = mock()
    private val firebaseUser: FirebaseUser = mock()

    @Before
    fun setUp() {
        mockedFirebaseAuth = mockStatic(FirebaseAuth::class.java)
        whenever(FirebaseAuth.getInstance()).thenReturn(firebaseAuth)
        whenever(firebaseAuth.currentUser).thenReturn(firebaseUser)
        whenever(firebaseUser.uid).thenReturn("test_uid")
    }

    @After
    fun tearDown() {
        mockedFirebaseAuth.close()
    }

    @Test
    fun addVisit_success_test() {
        val repo = mock<TherapyVisitRepoImpl>()
        val viewModel = TherapyVisitViewModel(repo)
        val testVisit = TherapyVisitModel(
            visitId = "v1",
            name = "John Doe",
            therapyType = "Physical",
            visitDate = "10/10/2023",
            location = "Central Clinic"
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Visit added successfully")
            null
        }.whenever(repo).addVisit(any(), any())

        var successResult = false
        var messageResult = ""

        viewModel.addVisit(testVisit) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Visit added successfully", messageResult)

        verify(repo).addVisit(any(), any())
    }
}

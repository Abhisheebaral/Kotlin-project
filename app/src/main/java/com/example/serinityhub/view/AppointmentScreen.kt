package com.example.serinityhub.view

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serinityhub.model.TherapyVisitModel
import com.example.serinityhub.repository.TherapyVisitRepoImpl
import com.example.serinityhub.ui.theme.Blue1
import com.example.serinityhub.ui.theme.SkyBlueBg
import com.example.serinityhub.viewmodel.TherapyVisitViewModel

@Composable
fun AppointmentScreen() {
    val viewModel = remember { TherapyVisitViewModel(TherapyVisitRepoImpl()) }
    val context = LocalContext.current

    var showEditDialog by remember { mutableStateOf(false) }
    var editingAppointment by remember { mutableStateOf<TherapyVisitModel?>(null) }

    // Fetch all appointments on screen load
    LaunchedEffect(Unit) {
        viewModel.getAllVisits { }
    }

    // Observe appointments directly
    val allAppointments = viewModel.getAllVisitsAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SkyBlueBg)
            .padding(16.dp)
    ) {
        Text(
            text = "Your Appointments",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Add Appointment Button
        Button(
            onClick = {
                // Launch the AddTherapyVisitActivity form
                context.startActivity(Intent(context, AddTherapyVisitActivity::class.java))
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Blue1)
        ) {
            Text("Add Appointment")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (allAppointments.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No appointments found")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(allAppointments) { appointment ->
                    AppointmentListItem(
                        appointment = appointment,
                        onEdit = {
                            editingAppointment = appointment
                            showEditDialog = true
                        },
                        onDelete = {
                            viewModel.deleteVisit(appointment.visitId) { success, msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }
    }

    // Edit Dialog
    if (showEditDialog && editingAppointment != null) {
        EditAppointmentDialog(
            appointment = editingAppointment!!,
            onDismiss = { showEditDialog = false },
            onConfirm = { updated ->
                viewModel.updateVisit(updated) { success, msg ->
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    showEditDialog = false
                }
            }
        )
    }
}

@Composable
fun AppointmentListItem(
    appointment: TherapyVisitModel,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(appointment.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Therapy Type: ${appointment.therapyType}")
                Text("Date: ${appointment.visitDate}")
                Text("Location: ${appointment.location}")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Appointment", tint = Blue1)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Appointment", tint = Color.Red)
                }
            }
        }
    }
}

@Composable
fun EditAppointmentDialog(
    appointment: TherapyVisitModel,
    onDismiss: () -> Unit,
    onConfirm: (TherapyVisitModel) -> Unit
) {
    var name by remember { mutableStateOf(appointment.name) }
    var therapyType by remember { mutableStateOf(appointment.therapyType) }
    var visitDate by remember { mutableStateOf(appointment.visitDate) }
    var location by remember { mutableStateOf(appointment.location) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Appointment") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    colors = TextFieldDefaults.colors(focusedIndicatorColor = Blue1, unfocusedIndicatorColor = Blue1)
                )
                OutlinedTextField(
                    value = therapyType,
                    onValueChange = { therapyType = it },
                    label = { Text("Therapy Type") },
                    colors = TextFieldDefaults.colors(focusedIndicatorColor = Blue1, unfocusedIndicatorColor = Blue1)
                )
                OutlinedTextField(
                    value = visitDate,
                    onValueChange = { visitDate = it },
                    label = { Text("Date") },
                    colors = TextFieldDefaults.colors(focusedIndicatorColor = Blue1, unfocusedIndicatorColor = Blue1)
                )
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    colors = TextFieldDefaults.colors(focusedIndicatorColor = Blue1, unfocusedIndicatorColor = Blue1)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        appointment.copy(
                            name = name,
                            therapyType = therapyType,
                            visitDate = visitDate,
                            location = location
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Blue1)
            ) {
                Text("Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel", color = Blue1) }
        }
    )
}

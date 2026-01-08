package com.example.serinityhub.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serinityhub.model.TherapyVisitModel
import com.example.serinityhub.repository.TherapyVisitRepoImpl
import com.example.serinityhub.ui.theme.Blue1
import com.example.serinityhub.ui.theme.SerinityhubTheme
import com.example.serinityhub.ui.theme.SkyBlueBg
import com.example.serinityhub.viewmodel.TherapyVisitViewModel
import java.util.*

class AddTherapyVisitActivity : ComponentActivity() {

    private val viewModel = TherapyVisitViewModel(TherapyVisitRepoImpl())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SerinityhubTheme {
                AddTherapyVisitScreen(viewModel)
            }
        }
    }
}

@Composable
fun AddTherapyVisitScreen(viewModel: TherapyVisitViewModel) {

    var name by remember { mutableStateOf("") }
    var therapyType by remember { mutableStateOf("") }
    var visitDate by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePicker = DatePickerDialog(
        context,
        { _, y, m, d -> visitDate = "$d/${m + 1}/$y" },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(containerColor = SkyBlueBg) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("Record New Appointment", fontSize = 22.sp, modifier = Modifier.padding(8.dp))

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(focusedIndicatorColor = Blue1, unfocusedIndicatorColor = Blue1)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = therapyType,
                onValueChange = { therapyType = it },
                label = { Text("Therapy Type") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(focusedIndicatorColor = Blue1, unfocusedIndicatorColor = Blue1)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = visitDate,
                onValueChange = {},
                label = { Text("Date") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePicker.show() },
                enabled = false,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                    disabledIndicatorColor = Blue1
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(focusedIndicatorColor = Blue1, unfocusedIndicatorColor = Blue1)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (name.isBlank() || therapyType.isBlank() || visitDate.isBlank() || location.isBlank()) {
                        Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    isLoading = true
                    val visit = TherapyVisitModel(
                        name = name,
                        therapyType = therapyType,
                        visitDate = visitDate,
                        location = location
                    )
                    viewModel.addVisit(visit) { success, msg ->
                        isLoading = false
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        if (success) {
                            (context as? ComponentActivity)?.finish()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue1)
            ) {
                if (isLoading) CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                else Text("Add Appointment")
            }
        }
    }
}

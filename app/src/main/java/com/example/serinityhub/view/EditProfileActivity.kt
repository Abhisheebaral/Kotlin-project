package com.example.serinityhub.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serinityhub.model.UserModel
import com.example.serinityhub.viewmodel.UserViewModel

@Composable
fun EditProfileScreen(
    user: UserModel, // <-- pass user from ProfileScreen
    userViewModel: UserViewModel = UserViewModel(),
    onProfileSaved: (UserModel) -> Unit // <-- callback with updated user
) {

    var firstName by remember { mutableStateOf(user.firstName) }
    var lastName by remember { mutableStateOf(user.lastName) }
    var dob by remember { mutableStateOf(user.dob) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Edit Profile",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = dob,
            onValueChange = { dob = it },
            label = { Text("Date of Birth") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val updatedUser = user.copy(
                    firstName = firstName,
                    lastName = lastName,
                    dob = dob
                )

                // Update in database
                userViewModel.updateProfile(
                    user.userId,
                    mapOf(
                        "firstName" to firstName,
                        "lastName" to lastName,
                        "dob" to dob
                    )
                ) { success, _ ->
                    if (success) {
                        onProfileSaved(updatedUser) // return updated user
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}

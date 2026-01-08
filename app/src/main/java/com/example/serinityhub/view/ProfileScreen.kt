package com.example.serinityhub.view

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serinityhub.R
import com.example.serinityhub.model.UserModel
import com.example.serinityhub.viewmodel.UserViewModel

@Composable
fun ProfileScreen() {

    val context = LocalContext.current
    val userViewModel = UserViewModel()

    var user by remember { mutableStateOf<UserModel?>(null) }
    var showEditProfile by remember { mutableStateOf(false) }

    val userId = userViewModel.getCurrentUserId()

    LaunchedEffect(Unit) {
        if (userId != null) {
            userViewModel.fetchUserProfile(userId) { success, data, _ ->
                if (success) {
                    user = data
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if (showEditProfile && user != null) {
            EditProfileScreen(
                user = user!!,
                onProfileSaved = { updatedUser ->
                    user = updatedUser
                    showEditProfile = false
                }
            )
        } else {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.baseline_person_24),
                        contentDescription = "Profile Icon",
                        modifier = Modifier.size(90.dp),
                        tint = Color(0xFF2EC4B6)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "My Profile",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    ProfileInfoItem("Email", user?.email ?: "Not Available")
                    ProfileInfoItem("First Name", user?.firstName ?: "Not Available")
                    ProfileInfoItem("Last Name", user?.lastName ?: "Not Available")
                    ProfileInfoItem("Date of Birth", user?.dob ?: "Not Available")

                    Spacer(modifier = Modifier.height(24.dp))

                    // -------- EDIT PROFILE --------
                    Button(
                        onClick = {
                            showEditProfile = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2EC4B6)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = "Edit Profile",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // -------- LOGOUT --------
                    Button(
                        onClick = {
                            userViewModel.logout { _, _ ->
                                context.startActivity(
                                    Intent(context, LoginActivity::class.java)
                                )
                                (context as Activity).finish()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2EC4B6)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = "Logout",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // -------- DELETE PROFILE --------
                    Button(
                        onClick = {
                            if (userId != null) {
                                userViewModel.deleteProfile(userId) { _, _ ->
                                    context.startActivity(
                                        Intent(context, LoginActivity::class.java)
                                    )
                                    (context as Activity).finish()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Delete Profile", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileInfoItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

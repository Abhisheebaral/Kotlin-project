package com.example.serinityhub.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serinityhub.R

@Composable
fun HomeScreen() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Find Your Therapist",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2EC4B6)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search ") },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(16.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Image below search bar
        Image(
            painter = painterResource(id = R.drawable.home),
            contentDescription = "Home Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)  // adjust height as needed
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Facilities",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CategoryButton("Psychiatrist")
            CategoryButton("Psychotherapist")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CategoryButton("Counseling")
            CategoryButton("Therapy")
        }
    }
}

@Composable
fun RowScope.CategoryButton(name: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .weight(1f)
            .height(50.dp)
            .background(Color(0xFF2EC4B6), RoundedCornerShape(16.dp))
            .clickable { }
    ) {
        Text(
            text = name,
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

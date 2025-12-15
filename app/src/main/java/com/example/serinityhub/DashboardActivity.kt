package com.example.serinityhub

import com.example.serinityhub.ui.theme.Blue1
import com.example.serinityhub.ui.theme.White1


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource


class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashboardBody()
        }
    }
}

data class NavItem(val label: String, val icon: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardBody() {
    val context = LocalContext.current
    val activity = context as Activity

    var selectedIndex by remember { mutableStateOf(0) }
    val navList = listOf(
        NavItem("Home", R.drawable.baseline_home_24),
        NavItem("Search", R.drawable.baseline_search_24),
        NavItem("Notifications", R.drawable.baseline_notifications_24),
        NavItem("Profile", R.drawable.baseline_person_24)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Serenity Hub") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue1,
                    navigationIconContentColor = White1,
                    titleContentColor = White1,
                    actionIconContentColor = White1
                ),
                actions = {
                    IconButton(onClick = { /* Settings */ }) {
                        Icon(painter = painterResource(R.drawable.baseline_settings_24), contentDescription = "Settings")
                    }
                    IconButton(onClick = { /* History */ }) {
                        Icon(painter = painterResource(R.drawable.baseline_history_24), contentDescription = "History")
                    }
                    IconButton(onClick = { /* More */ }) {
                        Icon(painter = painterResource(R.drawable.baseline_more_horiz_24), contentDescription = "More")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                navList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(painter = painterResource(item.icon), contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val intent = Intent(context, ProductActivity::class.java)
                context.startActivity(intent)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (selectedIndex) {
                0 -> HomeScreen()
                1 -> SearchScreen()
                2 -> NotificationScreen()
                3 -> ProfileScreen()
            }
        }
    }
}

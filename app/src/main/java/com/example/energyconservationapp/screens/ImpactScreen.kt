package com.example.energyconservationapp.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energyconservationapp.data.AboutData

@Composable
fun ImpactScreen() {
    val context = LocalContext.current
    
    // Pledge Counter State
    var pledgeCount by remember { mutableStateOf(342) } // Started at a mock number to look good for the demo
    var hasPledged by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Environmental Impact Statistics
        item {
            Text("🌍 Global Impact", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Static Chart Placeholder for Global Energy Demand
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "📊 Global Energy Demand Chart\n(Imagine a line graph rising by 47% here)", 
                        color = Color(0xFF2E7D32),
                        textAlign = TextAlign.Center
                    )
                    // Note for Medha: If we have an actual image asset later, use Image() here.
                }
            }
        }

        item {
            Text("Did you know?", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            AboutData.impactStats.forEach { stat ->
                Text("• $stat", modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp), fontSize = 14.sp)
            }
        }

        // 2. University Sustainability Pledge Section
        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text("🌱 Sustainability Pledge", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                "Join our university in pledging to reduce daily energy consumption by at least 10%. Every action counts!", 
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 14.sp
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = { 
                        if (!hasPledged) {
                            pledgeCount++
                            hasPledged = true
                        }
                    },
                    enabled = !hasPledged
                ) {
                    Text(if (hasPledged) "Pledged!" else "Take the Pledge")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Total Pledges: $pledgeCount", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
            }
        }

        // 3. Share Feature using Android Intent
        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Button(
                onClick = { shareApp(context) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1))
            ) {
                Icon(Icons.Default.Share, contentDescription = "Share Icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share Awareness Message")
            }
        }

        // 4. Team Member Profiles
        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text("👥 Meet the Team", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        items(AboutData.teamMembers) { member ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = member.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = member.rollNo, color = Color.Gray, fontSize = 14.sp)
                    Text(
                        text = "Role: ${member.contribution}", 
                        fontSize = 14.sp, 
                        modifier = Modifier.padding(top = 4.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
        
        item { 
            Spacer(modifier = Modifier.height(24.dp)) // Bottom padding for navigation bar clearance
        }
    }
}

// Function to handle the Android Share Intent
private fun shareApp(context: Context) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "🌍 I just took the pledge to save energy with our University's Energy Conservation App! Join me and let's reduce our carbon footprint together. #GoGreen #EnergySaver"
        )
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share Awareness Via"))
}

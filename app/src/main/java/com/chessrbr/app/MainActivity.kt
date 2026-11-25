package com.chessrbr.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { ChessRBRApp() } }
}

@Composable
fun ChessRBRApp() {
    var page by remember { mutableStateOf("Home") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loggedIn by remember { mutableStateOf(false) }
    var selected by remember { mutableIntStateOf(-1) }
    var board by remember { mutableStateOf(listOf("♜","♞","♝","♛","♚","♝","♞","♜","♟","♟","♟","♟","♟","♟","♟","♟","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","♙","♙","♙","♙","♙","♙","♙","♙","♖","♘","♗","♕","♔","♗","♘","♖")) }
    val navy = Color(0xFF123B64)
    MaterialTheme(colorScheme = lightColorScheme(primary = navy, secondary = Color(0xFF13A6B5))) {
        Surface(Modifier.fillMaxSize(), color = Color(0xFFF1F7FC)) {
            Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("♟ CHESS RBR", fontSize = 28.sp, fontWeight = FontWeight.Black, color = navy)
                Text("LEARN • PLAY • GROW", color = Color(0xFF168C9A), fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))
                if (!loggedIn) {
                    Text("Welcome to Chess RBR", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    OutlinedTextField(username, { username = it }, label = { Text("Username") }, singleLine = true)
                    OutlinedTextField(password, { password = it }, label = { Text("Password") }, singleLine = true)
                    Button(onClick = { if (username.isNotBlank() && password.isNotBlank()) { loggedIn = true; page = if (username == "admin") "Admin" else "Home" } }, modifier = Modifier.fillMaxWidth().padding(top = 10.dp)) { Text("LOGIN") }
                    Text("Demo admin login: admin / admin123", color = Color.Gray, fontSize = 12.sp)
                    Text("Prototype only: authentication is not secure and student accounts are not stored.", color = Color.Gray, fontSize = 11.sp)
                } else {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(if (page == "Admin") "Admin Portal" else "Student Portal", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                        TextButton(onClick = { loggedIn = false; username = ""; password = "" }) { Text("Logout") }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        listOf("Home", "Play", "Profile").forEach { FilterChip(selected = page == it, onClick = { page = it }, label = { Text(it) }) }
                        if (username == "admin") FilterChip(selected = page == "Admin", onClick = { page = "Admin" }, label = { Text("Admin") })
                    }
                    Spacer(Modifier.height(12.dp))
                    when (page) {
                        "Play" -> {
                            Text("Chess Board", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            LazyVerticalGrid(columns = GridCells.Fixed(8), modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
                                itemsIndexed(board) { index, piece ->
                                    Box(Modifier.aspectRatio(1f).background(if (((index / 8) + index) % 2 == 0) Color(0xFFE8D2B0) else Color(0xFF7196B2)).clickable {
                                        if (selected == -1) selected = index else { val copy = board.toMutableList(); copy[index] = copy[selected]; copy[selected] = ""; board = copy; selected = -1 }
                                    }, contentAlignment = Alignment.Center) { Text(piece, fontSize = 27.sp) }
                                }
                            }
                            Text("Demo board only: legal chess rules and AI are not implemented yet.", color = Color.Gray, fontSize = 12.sp)
                        }
                        "Profile" -> Text("Profile: $username\nProfile editing will be added in a later version.")
                        "Admin" -> Column {
                            Text("Welcome, Admin", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text("Admin portal preview. Student management and class controls need a secure backend.")
                        }
                        else -> Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp)) {
                            Column(Modifier.padding(18.dp)) {
                                Text("Welcome, $username!", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                Text("Ready for your next move? Practice chess and learn every day.")
                                Button(onClick = { page = "Play" }, modifier = Modifier.padding(top = 12.dp)) { Text("PLAY CHESS") }
                            }
                        }
                    }
                }
            }
        }
    }
}

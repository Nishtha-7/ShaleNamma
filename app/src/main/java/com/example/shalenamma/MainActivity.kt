package com.example.shalenamma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shalenamma.ui.theme.ShaleNammaTheme
import androidx.navigation.compose.*
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.TextFieldValue
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.platform.LocalContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShaleNammaTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {

        composable("dashboard") {
            DashboardScreen(navController)
        }

        composable("meal") {
            DailyMealScreen()
        }


        composable("tour") {
            SimpleScreen("Facility Tour Screen")
        }

        composable("stars") {
            SimpleScreen("Student Stars Screen")
        }

        composable("feedback") {
            FeedbackScreen()
        }
    }
}

@Composable
fun DashboardScreen(navController: NavHostController) {
    var imageUrl by remember { mutableStateOf("") }
    var mealText by remember { mutableStateOf("") }

    val date = java.text.SimpleDateFormat("yyyy-MM-dd")
        .format(java.util.Date())

    LaunchedEffect(Unit) {

        val dbRef = com.google.firebase.database.FirebaseDatabase
            .getInstance()
            .reference
            .child("daily_meals")
            .child(date)

        dbRef.get().addOnSuccessListener { snapshot ->

            if (snapshot.exists()) {

                imageUrl = snapshot.child("imageUrl")
                    .value.toString()

                mealText = snapshot.child("menu")
                    .value.toString()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        // 🔶 TOP HEADER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    Color(0xFFE09A3E),
                    shape = RoundedCornerShape(
                        bottomStart = 30.dp,
                        bottomEnd = 30.dp
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0x33FFFFFF)
                        )
                    ) {
                        Text(
                            text = "ENG",
                            modifier = Modifier.padding(
                                horizontal = 14.dp,
                                vertical = 6.dp
                            ),
                            color = Color.White
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Column {

                    Text(
                        text = "Shale-Namma",
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Government School, Bengaluru",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🍛 TODAY'S MEAL
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "TODAY'S MID-DAY MEAL",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Button(
                    onClick = {
                        navController.navigate("meal")
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("UPLOAD")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column {

                    // 🍛 STATIC IMAGE
                    Image(
                        painter = rememberAsyncImagePainter(
                            "https://images.unsplash.com/photo-1547592180-85f173990554"
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Veg Pulao + Boiled Egg + Apple",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Healthy and nutritious mid-day meal",
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🏫 FACILITY TOUR
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "FACILITY TOUR",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Text(
                    text = "VIEW ALL",
                    color = Color(0xFF3B82F6),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                FacilityCard(
                    "https://images.unsplash.com/photo-1562774053-701939374585"
                )

                FacilityCard(
                    "https://images.unsplash.com/photo-1524995997946-a1c2e315a42f"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 💬 FEEDBACK CARD
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {

                        Text(
                            text = "Parent Feedback",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Text(
                            text = "Your voice matters",
                            color = Color.Gray
                        )
                    }

                    Button(
                        onClick = {
                            navController.navigate("feedback")
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("SEND")
                    }
                }
            }
        }
    }
}
@Composable
fun DashboardButton(title: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun SimpleScreen(title: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun DailyMealScreen() {

    var mealText by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        // 🔶 TOP HEADER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    Color(0xFFE09A3E),
                    shape = RoundedCornerShape(
                        bottomStart = 30.dp,
                        bottomEnd = 30.dp
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Upload Meal",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Upload today's mid-day meal",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        // 📦 CONTENT
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            // 🖼 IMAGE CARD
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (imageUri != null) {

                        Image(
                            painter = rememberAsyncImagePainter(imageUri),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            contentScale = ContentScale.Crop
                        )

                    } else {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "No Image Selected",
                                color = Color.Gray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            launcher.launch("image/*")
                        },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("📷 Select Image")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 📝 TEXT FIELD
            OutlinedTextField(
                value = mealText,
                onValueChange = { mealText = it },
                label = { Text("Enter Meal Menu") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🚀 SUBMIT BUTTON
            Button(
                onClick = {

                    if (imageUri != null && mealText.isNotEmpty()) {

                        val date = java.text.SimpleDateFormat("yyyy-MM-dd")
                            .format(java.util.Date())

                        val dbRef = com.google.firebase.database.FirebaseDatabase
                            .getInstance()
                            .reference
                            .child("daily_meals")
                            .child(date)

                        dbRef.get().addOnSuccessListener { snapshot ->

                            if (snapshot.exists()) {

                                android.widget.Toast.makeText(
                                    context,
                                    "Meal already uploaded today!",
                                    android.widget.Toast.LENGTH_SHORT
                                ).show()

                            } else {

                                val storageRef = com.google.firebase.storage.FirebaseStorage
                                    .getInstance()
                                    .reference
                                    .child("meals/$date.jpg")

                                storageRef.putFile(imageUri!!)
                                    .addOnSuccessListener {

                                        storageRef.downloadUrl
                                            .addOnSuccessListener { uri ->

                                                val data = mapOf(
                                                    "imageUrl" to uri.toString(),
                                                    "menu" to mealText
                                                )

                                                dbRef.setValue(data)

                                                android.widget.Toast.makeText(
                                                    context,
                                                    "Uploaded Successfully!",
                                                    android.widget.Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                    }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp)
            ) {

                Text(
                    text = "UPLOAD MEAL",
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun FacilityCard(imageUrl: String) {

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.size(150.dp)
    ) {

        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun FeedbackScreen() {

    var feedbackText by remember { mutableStateOf("") }
    var isAnonymous by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        // 🔶 TOP HEADER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    Color(0xFFE09A3E),
                    shape = RoundedCornerShape(
                        bottomStart = 30.dp,
                        bottomEnd = 30.dp
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Parent Feedback",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Your voice matters to the school",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        // 📦 CONTENT
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Write Your Suggestion",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = feedbackText,
                        onValueChange = { feedbackText = it },
                        label = { Text("Enter feedback") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = isAnonymous,
                            onCheckedChange = {
                                isAnonymous = it
                            }
                        )

                        Text(
                            text = "Submit anonymously"
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {

                            if (feedbackText.isNotEmpty()) {

                                val dbRef = com.google.firebase.database
                                    .FirebaseDatabase
                                    .getInstance()
                                    .reference
                                    .child("feedbacks")

                                val feedbackData = mapOf(
                                    "feedback" to feedbackText,
                                    "anonymous" to isAnonymous,
                                    "timestamp" to System.currentTimeMillis()
                                )

                                dbRef.push().setValue(feedbackData)

                                android.widget.Toast.makeText(
                                    context,
                                    "Feedback Submitted!",
                                    android.widget.Toast.LENGTH_SHORT
                                ).show()

                                feedbackText = ""
                                isAnonymous = false
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {

                        Text(
                            text = "SEND FEEDBACK",
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}
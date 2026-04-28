package com.example.energyconservationapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.energyconservationapp.ui.theme.EnergyConservationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EnergyConservationAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    QuizScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: Int
)

@Composable
fun QuizScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val questions = listOf(

        QuizQuestion(
            question = "Which bulb saves more electricity?",
            options = listOf("LED Bulb", "Incandescent Bulb", "Tube Fire Lamp", "Halogen"),
            correctAnswer = 0
        ),

        QuizQuestion(
            question = "What should you do before leaving a room?",
            options = listOf("Open windows", "Turn off lights/fan", "Use charger", "Increase AC"),
            correctAnswer = 1
        ),

        QuizQuestion(
            question = "Which transport option is greener?",
            options = listOf("Car Alone", "Bike Alone", "Bus / Metro", "Taxi"),
            correctAnswer = 2
        ),

        QuizQuestion(
            question = "Laptop charging should be:",
            options = listOf("Always plugged in", "Unplug after charge", "Overnight daily", "Never charge"),
            correctAnswer = 1
        ),

        QuizQuestion(
            question = "Saving electricity helps reduce:",
            options = listOf("Pollution", "Awareness", "Books", "Attendance"),
            correctAnswer = 0
        )
    )

    var currentQuestion by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf(-1) }
    var score by remember { mutableStateOf(0) }
    var showResult by remember { mutableStateOf(false) }

    val totalQuestions = questions.size

    if (showResult) {

        ResultScreen(
            score = score,
            total = totalQuestions,
            onRestart = {
                currentQuestion = 0
                selectedOption = -1
                score = 0
                showResult = false
            }
        )

    } else {

        val question = questions[currentQuestion]
        val progress = (currentQuestion + 1).toFloat() / totalQuestions.toFloat()

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(18.dp)
                .verticalScroll(rememberScrollState()),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "🌱 Green Quiz Challenge",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Question ${currentQuestion + 1} of $totalQuestions",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            Card(
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = question.question,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    question.options.forEachIndexed { index, option ->

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            RadioButton(
                                selected = selectedOption == index,
                                onClick = {
                                    selectedOption = index
                                }
                            )

                            Text(
                                text = option,
                                fontSize = 17.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    if (selectedOption == -1) {
                        Toast.makeText(
                            context,
                            "Please select an answer",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        if (selectedOption == question.correctAnswer) {
                            score++

                            Toast.makeText(
                                context,
                                "Correct Answer ✅",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {

                            Toast.makeText(
                                context,
                                "Incorrect Answer ❌",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        if (currentQuestion < totalQuestions - 1) {
                            currentQuestion++
                            selectedOption = -1
                        } else {
                            showResult = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = {
                    selectedOption = -1
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reset Current Selection")
            }
        }
    }
}

@Composable
fun ResultScreen(
    score: Int,
    total: Int,
    onRestart: () -> Unit
) {

    val badge = when {
        score <= 2 -> "🌿 Beginner"
        score <= 4 -> "⚡ Energy Saver"
        else -> "🏆 Green Champion"
    }

    val badgeColor = when {
        score <= 2 -> Color(0xFF81C784)
        score <= 4 -> Color(0xFFFFD54F)
        else -> Color(0xFF4CAF50)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Quiz Completed!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Your Score: $score / $total",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        Box(
            modifier = Modifier
                .background(
                    color = badgeColor,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 24.dp, vertical = 14.dp)
        ) {
            Text(
                text = badge,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onRestart,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Play Again")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQuiz() {
    EnergyConservationAppTheme {
        QuizScreen()
    }
}
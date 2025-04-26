package com.bookblitzpremium.upcomingproject

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalUserViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteUserViewModel
import com.bookblitzpremium.upcomingproject.data.model.AuthState
import com.bookblitzpremium.upcomingproject.data.model.SignupState
import com.bookblitzpremium.upcomingproject.ui.components.CheckStatusLoading
import java.nio.file.WatchEvent

// Main entry point for the navigation
@Composable
fun Regrister() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "step1") {
        composable("step1") { Step1Screen(navController,true) }
        composable("step2") { LoginScreen1(navController,true) }
        composable("step3") { Step3Screen(navController,true) }
    }
}

@Composable
fun Step1Screen(
    navController: NavController,
    tabletScreen: Boolean,
    viewModel : AuthViewModel = hiltViewModel(),
    localViewModel: LocalUserViewModel = hiltViewModel(),
    remoteUserViewModel : RemoteUserViewModel = hiltViewModel()
) {

    val valueVertical: Dp = if (tabletScreen) 50.dp else 100.dp

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    var toastMessage by remember { mutableStateOf<String?>(null) } // State for Toast message
    var toastTrigger by remember { mutableStateOf(0) } // Unique trigger for Toast
    val signupState by viewModel.signupState.collectAsState()
    var triggerSignup by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(signupState) {
        if (signupState is SignupState.Error) {
            toastMessage = (signupState as SignupState.Error).message
            toastTrigger++
            viewModel.clearSignUpState()
            triggerSignup = false
        }
    }

    LaunchedEffect(toastMessage, toastTrigger) {
        toastMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            toastMessage = null
        }
    }

    //validation
    fun getPasswordErrorMessage(password: String): String? {
        if (password.length < 8) {
            return "Password must be at least 8 characters long"
        }
//        if (!password.contains(Regex(".*[A-Z].*[A-Z].*"))) {
//            return "Password must contain at least 2 uppercase letters"
//        }
//        if (!password.contains(Regex(".*[!@#$&*].*"))) {
//            return "Password must contain at least 1 special character (!@#$&*)"
//        }
//        if (!password.contains(Regex(".*[0-9].*[0-9].*"))) {
//            return "Password must contain at least 2 digits"
//        }
//        if (!password.contains(Regex(".*[a-z].*[a-z].*[a-z].*"))) {
//            return "Password must contain at least 3 lowercase letters"
//        }
        return null
    }

    fun doPasswordsMatch(): Boolean = password == confirmPassword

    fun isFormValid(): Boolean {
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return false
        }

        getPasswordErrorMessage(password)?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            return false
        }

        if (confirmPassword.isBlank()) {
            Toast.makeText(context, "Please confirm your password", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!doPasswordsMatch()) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(valueVertical))

        // Header: Title and Stepper
        Text(
            text = "Sign Up for Free",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "register to get loving trip package",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray
            ),
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Stepper (1/3) with clickable bubbles
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val currentStep = 1 // This can be dynamic based on the current route
            repeat(3) { index ->
                val stepNumber = index + 1
                val isActive = stepNumber == currentStep
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = if (isActive) Color.Black else Color.LightGray,
                            shape = RoundedCornerShape(32.dp)
                        )
                        .clickable {
                            // Navigate to the corresponding step
                            when (stepNumber) {
                                1 -> navController.navigate("step1") { popUpTo(navController.graph.startDestinationId) }
                                2 -> navController.navigate("step2") { popUpTo(navController.graph.startDestinationId) }
                                3 -> navController.navigate("step3") { popUpTo(navController.graph.startDestinationId) }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$stepNumber",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
                if (index < 2) { // Draw line between steps
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(2.dp)
                            .background(Color.LightGray)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Main Content: Personal Information
        Text(
            text = "PERSONAL INFORMATION",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        Text(
            text = "Provide the basic information to get you registered with us.",
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 16.dp)
        )



        // Business Name
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Business email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textStyle = TextStyle(fontSize = 16.sp),
            singleLine = true
        )

        // Business Email
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Business password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            textStyle = TextStyle(fontSize = 16.sp),
            singleLine = true
        )

        // Business Email
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Business confirmPassword") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            textStyle = TextStyle(fontSize = 16.sp),
            singleLine = true
        )


        Spacer(modifier = Modifier.weight(1f))

        if (valueVertical == 20.dp) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Content for Row layout
                Button(
                    onClick = {
                        navController.navigate("step2")
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Sign up a account",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Next Button
                Button(
                    onClick = {
                        if(isFormValid()){
                            triggerSignup = true
                        }
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (valueVertical == 20.dp) Color.Black else Color.White
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Next",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Content for Column layout
                Button(
                    onClick = {
                        navController.navigate("step2")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Sign up a account",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Next Button
                Button(
                    onClick = {
                        if(isFormValid()){
                            triggerSignup = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Next",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
            }
        }

        if(triggerSignup) {
            CheckStatusLoading(
                isLoading = signupState is SignupState.Loading,
                backgroundAlpha = 0.5f,
                indicatorColor = MaterialTheme.colorScheme.primary,
            )

            LaunchedEffect(Unit) {
                val exists = localViewModel.checkUserEmail(email)
                if (!exists) {
                    val uid = viewModel.signup(email, password)
                    if (uid.isNotEmpty()) {
                        val username = email.substringBefore("@")
                        val user = User(uid = uid, username = username, email = email, password = password)
                        remoteUserViewModel.addUser(user)
                        localViewModel.insertNewUser(user)
                        viewModel.clearSignUpState()
                        triggerSignup = false
                    }
                } else {
                    viewModel.setSignupError("Email is already registered")
                    triggerSignup = false
                }
            }
        }
    }
}

@Composable
fun Step2Screen(navController: NavController, tabletScreen: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val valueVertical: Dp = if (tabletScreen) 20.dp else 100.dp

        Spacer(modifier = Modifier.height(valueVertical))

        // Stepper (1/3) with clickable bubbles
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val currentStep = 2 // This can be dynamic based on the current route
            repeat(3) { index ->
                val stepNumber = index + 1
                val isActive = stepNumber == currentStep
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = if (isActive) Color.Black else Color.LightGray,
                            shape = RoundedCornerShape(32.dp)
                        )
                        .clickable {
                            // Navigate to the corresponding step
                            when (stepNumber) {
                                1 -> navController.navigate("step1") { popUpTo(navController.graph.startDestinationId) }
                                2 -> navController.navigate("step2") { popUpTo(navController.graph.startDestinationId) }
                                3 -> navController.navigate("step3") { popUpTo(navController.graph.startDestinationId) }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$stepNumber",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
                if (index < 2) { // Draw line between steps
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(2.dp)
                            .background(Color.LightGray)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        CountrySelectionScreen()

        Spacer(modifier = Modifier.weight(1f))

        // Next Button
        Button(
            onClick = {
                navController.navigate("step3")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Next",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun Step3Screen(navController: NavController, tabletScreen: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val valueVertical: Dp = if (tabletScreen) 50.dp else 100.dp

        Spacer(modifier = Modifier.height(valueVertical))
        // Header: Title and Stepper
        Text(
            text = "Thanks for joining us",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "register to get loving trip package",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray
            ),
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Stepper (1/3) with clickable bubbles
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val currentStep = 3 // This can be dynamic based on the current route
            repeat(3) { index ->
                val stepNumber = index + 1
                val isActive = stepNumber == currentStep
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = if (isActive) Color.Black else Color.LightGray,
                            shape = RoundedCornerShape(32.dp)
                        )
                        .clickable {
                            // Navigate to the corresponding step
                            when (stepNumber) {
                                1 -> navController.navigate("step1") { popUpTo(navController.graph.startDestinationId) }
                                2 -> navController.navigate("step2") { popUpTo(navController.graph.startDestinationId) }
                                3 -> navController.navigate("step3") { popUpTo(navController.graph.startDestinationId) }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$stepNumber",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
                if (index < 2) { // Draw line between steps
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(2.dp)
                            .background(Color.LightGray)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Main Content: Personal Information
        Text(
            text = "PERSONAL INFORMATION",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        Text(
            text = "Provide the basic information to get you registered with us.",
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 16.dp)
        )



        Spacer(modifier = Modifier.weight(1f))

        // Next Button
        Button(
            onClick = {
                navController.navigate("step1")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Next",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestBubles(){
    CountrySelectionScreen()
}

@Composable
fun CountrySelectionScreen() {
    // State for the list of countries
    val initialCountries = listOf(
        "Australia", "Canada", "France",
        "Germany", "Ireland",
        "Italy", "Malaysia", "Netherlands",
        "UAE", "Poland",
        "Singapore", "Sweden", "United Kingdom",
        "United States", "Spain"
    )
    val countries = remember { mutableStateListOf<String>().apply { addAll(initialCountries) } }

    // State for selected countries
    var selectedCountries by rememberSaveable { mutableStateOf<List<String>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White, shape = RoundedCornerShape(32.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .background(Color.White, shape = RoundedCornerShape(32.dp)),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                items = countries,
                key = { index, country -> "$country-${selectedCountries.hashCode()}" },
                span = { index, _ ->
                    val rowIndex = calculateRowIndex(index)
                    val itemsInRow = if (rowIndex % 2 == 0) 3 else 2
                    val positionInRow = calculatePositionInRow(index)
                    val spanSize = if (positionInRow == itemsInRow - 1) 3 - positionInRow else 1
                    GridItemSpan(spanSize)
                }
            ) { index, country ->
                val isSelected = country in selectedCountries
                // Debug log to confirm selection state
                LaunchedEffect(isSelected) {
                    println("Country: $country, isSelected: $isSelected, selectedCountries: $selectedCountries")
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) Color(0xFFE0E0E0) else Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(32.dp))
                            .clickable {
                                selectedCountries = if (isSelected) {
                                    selectedCountries.toMutableList().apply { remove(country) }
                                } else {
                                    selectedCountries.toMutableList().apply { add(country) }
                                }
                            }
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = country,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        )
                    }
                }
            }
        }
    }
}

// Helper function to calculate the row index for a given item index
fun calculateRowIndex(itemIndex: Int): Int {
    var remainingItems = itemIndex
    var rowIndex = 0
    while (remainingItems >= 0) {
        val itemsInRow = if (rowIndex % 2 == 0) 3 else 2
        if (remainingItems < itemsInRow) return rowIndex
        remainingItems -= itemsInRow
        rowIndex++
    }
    return rowIndex
}

// Helper function to calculate the position within the row for a given item index
fun calculatePositionInRow(itemIndex: Int): Int {
    var remainingItems = itemIndex
    var rowIndex = 0
    while (remainingItems >= 0) {
        val itemsInRow = if (rowIndex % 2 == 0) 3 else 2
        if (remainingItems < itemsInRow) return remainingItems
        remainingItems -= itemsInRow
        rowIndex++
    }
    return 0
}

@Composable
fun EntryPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.content),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Foreground Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp), // optional padding
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    // Sign up action
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Sign up a account",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    // Next action
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Next",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }
    }
}

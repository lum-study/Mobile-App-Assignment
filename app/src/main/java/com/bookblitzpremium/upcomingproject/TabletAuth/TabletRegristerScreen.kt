package com.bookblitzpremium.upcomingproject.TabletAuth

import android.util.Patterns
import android.widget.Toast
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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bookblitzpremium.upcomingproject.GenderSelectionScreen
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteUserViewModel
import com.bookblitzpremium.upcomingproject.data.model.SignupState
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextFieldPassword
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

// Main entry point for the navigation

//vertical = true
//regrister = false

//@Composable
//fun RegristerVertical() {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = "step1") {
//
//        composable("step1") { Step1Screen(navController,false) }
//
//        composable(route = "step1/{email}/{password}",
//            arguments = listOf(
//                navArgument("email") { type = NavType.StringType },
//                navArgument("password") { type = NavType.StringType },
//            )) { backStackEntry ->
//            val email = backStackEntry.arguments?.getString("email").toString()
//            val password = backStackEntry.arguments?.getString("password").toString()
//
//            Step1Screen(
//                navController,
//                tabletScreen = false,
//                email = email,
//                password =password
//            )
//        }
//
//        composable(
//            route = "gender/{email}/{password}/{selectedGender}",
//            arguments = listOf(
//                navArgument("email") { type = NavType.StringType },
//                navArgument("password") { type = NavType.StringType },
//                navArgument("selectedGender") { type = NavType.StringType },
//            )
//        ) { backStackEntry ->
//            val email = backStackEntry.arguments?.getString("email").toString()
//            val password = backStackEntry.arguments?.getString("password").toString()
//            val selectedGender = backStackEntry.arguments?.getString("password").toString()
//
//            GenderSelectionScreen(navController,false, email,password)
//        }
//
//        composable(
//            route = "step2/{email}/{password}/{genderSelected}",
//            arguments = listOf(
//                navArgument("email") { type = NavType.StringType },
//                navArgument("password") { type = NavType.StringType },
//                navArgument("genderSelected") { type = NavType.StringType },
//            )
//        ) { backStackEntry ->
//            val email = backStackEntry.arguments?.getString("email").toString()
//            val password = backStackEntry.arguments?.getString("password").toString()
//            val genderSelected = backStackEntry.arguments?.getString("genderSelected").toString()
//
//            Step2Screen(navController, true, email ,password ,genderSelected ) // pass it to your screen
//        }
//
//    }
//}


fun String.encodeToUris(): String = this.toUri().toString().substringAfterLast("/")

@Composable
fun Step1Screen(
    navController: NavController,
    tabletScreen: Boolean,
    email :String  = "",
    password :String =""
) {
    val valueVertical: Dp = if (tabletScreen) 100.dp else 100.dp
    var email by rememberSaveable { mutableStateOf( email) }
    var password by rememberSaveable { mutableStateOf(password) }
    var confirmPassword by rememberSaveable { mutableStateOf(password) }
    val context = LocalContext.current
    var toastMessage by remember { mutableStateOf<String?>(null) } // State for Toast message
    var toastTrigger by remember { mutableStateOf(0) } // Unique trigger for Toast

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
        if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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

    val selectedGender = ""

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
                                1 -> navController.navigate("${AppScreen.Register.route}/${email.encodeToUri()}/${password.encodeToUri()}")
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

        CustomTextFieldPassword(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeholder = "Enter your Password",
            modifier = Modifier
                .fillMaxWidth()
        )

        CustomTextFieldPassword(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirm Password",
            placeholder = "Enter your Confirm Password",
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        if (tabletScreen == false) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        navController.navigate(AppScreen.Login.route)
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(48.dp)
                    ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Login account",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Next Button
                Button(
                    onClick = {
                        if(isFormValid()){
                            navController.navigate("${AppScreen.GenderScreen.route}/${email.encodeToUri()}/${password.encodeToUri()}/${selectedGender.encodeToUri()}")
                        }
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(48.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(24.dp)),
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
                        navController.navigate(AppScreen.Login.route)
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
                        text = "Login account",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Next Button
                Button(
                    onClick = {
                        if(isFormValid()){
                            navController.navigate("${AppScreen.GenderScreen.route}/${email.encodeToUri()}/${password.encodeToUri()}/${selectedGender.encodeToUri()}")
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
    }
}

@Composable
fun Step2Screen(
    navController: NavController,
    tabletScreen: Boolean,
    email: String,
    password: String,
    genderSelected: String,
    viewModel: AuthViewModel = hiltViewModel(),
    remoteUserViewModel: RemoteUserViewModel = hiltViewModel()
) {
    println("hello" + genderSelected)
    val context = LocalContext.current
    val signupState by viewModel.signupState.collectAsState()
    var toastMessage by remember { mutableStateOf<String?>(null) }
    var toastTrigger by remember { mutableStateOf(0) }
    var triggerSignup by rememberSaveable { mutableStateOf(false) }
    var signupJob by remember { mutableStateOf<Job?>(null) }

    // Show Toast for errors
    LaunchedEffect(toastMessage, toastTrigger) {
        toastMessage?.let { message ->
            withContext(Dispatchers.Main) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
            toastMessage = null
        }
    }

    // Trigger signup and handle state changes
    LaunchedEffect(triggerSignup, signupState) {
        if (triggerSignup) {
            println(genderSelected)
            triggerSignup = false // Reset immediately
            signupJob = viewModel.performSignup(email, password,genderSelected)
        }

        when (signupState) {
            is SignupState.Success -> {
                withContext(Dispatchers.Main) {
                    navController.navigate(AppScreen.Home.route) {
                        popUpTo(AppScreen.Register.route) { inclusive = true }
                    }
                }
                viewModel.clearSignUpState()
            }
            is SignupState.Error -> {
                toastMessage = (signupState as SignupState.Error).message
                toastTrigger++
                viewModel.clearSignUpState()
            }
            else -> {} // Idle or Loading
        }
    }

    // Cancel signup job when composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            signupJob?.cancel()
            signupJob = null
            viewModel.clearSignUpState()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = if (tabletScreen) R.drawable.hiking_potrait else R.drawable.hiking_new),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Content over the background image
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val valueVertical: Dp = if (tabletScreen) 100.dp else 60.dp
            Spacer(modifier = Modifier.height(valueVertical))

            // Card-like container
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(24.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

                // Stepper (1/3)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val currentStep = 3
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
                                .then(
                                    if (stepNumber <= currentStep) {
                                        Modifier.clickable {
                                            when (stepNumber) {
                                                1 -> navController.navigate("${AppScreen.Register.route}/${email.encodeToUri()}/${password.encodeToUri()}")
                                                2 -> navController.navigate("${AppScreen.GenderScreen.route}/${email.encodeToUri()}/${password.encodeToUri()}/${genderSelected.encodeToUri()}")
                                                3 -> navController.navigate("${AppScreen.WelcomeRegristerScreen.route}/${email.encodeToUri()}/${password.encodeToUri()}/${genderSelected.encodeToUri()}")
                                            }
                                        }
                                    } else {
                                        Modifier
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$stepNumber",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                        if (index < 2) {
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

                // Next Button
                Button(
                    onClick = {
                        triggerSignup = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RoundedCornerShape(24.dp),
                    enabled = signupState !is SignupState.Loading
                ) {
                    Text(
                        text = "Next",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(valueVertical))

            // Loading overlay
            if (signupState is SignupState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
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

//@Composable
//fun GenderSelectionScreen(
//    remoteUserViewModel: RemoteUserViewModel = hiltViewModel(),
//    navController: NavController,
//    userId: String
//) {
//    val selectedGender by remoteUserViewModel.selectedGender.collectAsState()
//    val loading by remoteUserViewModel.loading.collectAsState()
//    val error by remoteUserViewModel.error.collectAsState()
//
//    // Ensure userId is valid
//    if (userId.isEmpty()) {
//        LaunchedEffect(Unit) {
//            remoteUserViewModel.setError("User ID is missing")
//            navController.popBackStack()
//        }
//        return
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween
//    ) {
//        // Title
//        Text(
//            text = "What's your gender?",
//            style = MaterialTheme.typography.labelLarge,
//            modifier = Modifier.padding(top = 32.dp)
//        )
//
//        // Gender Options
//        Column(
//            modifier = Modifier.weight(1f),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            // Male Option
//            GenderOption(
//                gender = "Male",
//                isSelected = selectedGender == "Male",
//                icon = Icons.Default.Male,
//                onClick = { remoteUserViewModel.selectGender("Male") }
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            // Female Option
//            GenderOption(
//                gender = "Female",
//                isSelected = selectedGender == "Female",
//                icon = Icons.Default.Female,
//                onClick = { remoteUserViewModel.selectGender("Female") }
//            )
//        }
//
//        // Error Message
//        error?.let {
//            Text(
//                text = "Error: $it",
//                color = Color.Red,
//                modifier = Modifier.padding(bottom = 16.dp)
//            )
//        }
//
//        val conrotine = rememberCoroutineScope()
//
//        // Next Button
//        Button(
//            onClick = {
//                conrotine.launch {
//                    if (selectedGender != null) {
//                        remoteUserViewModel.updateUserGender(userId, selectedGender!!)
//                        navController.navigate("step2")
//                    } else {
//                        remoteUserViewModel.setError("Please select a gender")
//                    }
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp),
//            shape = RoundedCornerShape(28.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.Transparent
//            ),
//            border = BorderStroke(
//                width = 2.dp,
//                brush = Brush.linearGradient(
//                    colors = listOf(Color(0xFF8B5CF6), Color(0xFFEC4899)),
//                    start = Offset(0f, 0f),
//                    end = Offset(1000f, 1000f)
//                )
//            ),
//            enabled = !loading
//        ) {
//            Text(
//                text = "Next",
//                color = Color.Black,
//                style = MaterialTheme.typography.labelLarge
//            )
//        }
//    }
//}
//
//@Composable
//fun GenderOption(
//    gender: String,
//    isSelected: Boolean,
//    icon: ImageVector,
//    onClick: () -> Unit
//) {
//    Box(
//        modifier = Modifier
//            .size(120.dp)
//            .clickable { onClick() }
//            .background(
//                color = if (isSelected) Color(0xFFE5E7EB) else Color.Transparent,
//                shape = CircleShape
//            ),
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Icon(
//                imageVector = icon,
//                contentDescription = gender,
//                modifier = Modifier.size(64.dp),
//                tint = Color.Black
//            )
//            Text(
//                text = gender,
//                style = MaterialTheme.typography.titleMedium,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//            if (isSelected) {
//                Icon(
//                    imageVector = Icons.Default.Check,
//                    contentDescription = "Selected Nonlinear",
//                    tint = Color.Green,
//                    modifier = Modifier
//                        .size(24.dp)
//                        .align(Alignment.End)
//                        .offset(x = 16.dp, y = (-16).dp)
//                )
//            }
//        }
//    }
//}
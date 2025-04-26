package com.bookblitzpremium.upcomingproject

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.BottomNavigation
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalUserViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteUserViewModel
import com.bookblitzpremium.upcomingproject.data.model.AuthState
import com.bookblitzpremium.upcomingproject.ui.components.ButtonHeader
import com.bookblitzpremium.upcomingproject.ui.components.CheckStatusLoading
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextField
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextFieldPassword

import com.bookblitzpremium.upcomingproject.ui.navigation.AppNavigation
import com.bookblitzpremium.upcomingproject.ui.screen.auth.LoginPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.PermissionRequestScreen
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

enum class TravelScreen() {
    LOGIN,
    REGRISTER,
    HOME,
    PROFILE,
    BOOKINGS,
    Start,
    FORGETPASSWORD,
    OTP
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            AppTheme {
                val navController = rememberNavController()
//                InsertUser()
//                CheckEmail()
//                UserListScreen()
//                PermissionRequestScreen()
//                App()
//                Regrister()
//                UserListScreen()
//                userData()
                LoginScreen2(navController,true)
//                Login(
//                    navController = navController
//                )
//                Regrister()
//LoginPage(
//    false,
//    navController = navController,
//    email = "jedtanhoujie@gmail.com"
//)
            }
        }
    }
}

@Composable
fun userData(
    viewModel: LocalUserViewModel = hiltViewModel()
){
     viewModel.selectAllUser()

    val userdata by viewModel.users.collectAsState()


        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ){
            userdata.forEach { user ->
                Text(text = "Username: ${user.username}, UID: ${user.uid} , ")
            }
        }

}

@Composable
fun Login(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()
    val navigationCommand by viewModel.newNavigationCommand.collectAsState()

//    // Handle navigation on successful login
    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated ) {
            navController.navigate(AppScreen.Home.route)
            viewModel.clearNavigationCommand()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login to Your Account",
            style = AppTheme.typography.largeBold,
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.CenterHorizontally)
        )

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Username",
            placeholder = "Enter your username",
            leadingIcon = Icons.Default.Person,
            trailingIcon = Icons.Default.Clear,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextFieldPassword(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeholder = "Enter your Password",
            leadingIcon = Icons.Default.Lock,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ButtonHeader(
            textResId = R.string.login,
            valueHorizontal = 16.dp,
            onClick = {

//                viewModel.login(email, password)

            }
        )


        // Show loading indicator during login
        CheckStatusLoading(
            isLoading = authState is AuthState.Loading,
            backgroundAlpha = 0.5f,
            indicatorColor = MaterialTheme.colorScheme.primary
        )
    }
}

//@Composable
//fun UserListScreen(viewModel: LocalUserViewModel = hiltViewModel()) {
//    // Collect the StateFlow values as state in Compose
//    viewModel.selectAllUser()
//
//    val users by viewModel.users.collectAsState()
////    val loading by viewModel.loading.collectAsState()
////    val error by viewModel.error.collectAsState()
//
//    // Main UI
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        // Title
//        Text(
//            text = "User List",
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        // Loading indicator
//        if (loading) {
//            CircularProgressIndicator(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//                    .wrapContentWidth(align = Alignment.CenterHorizontally)
//            )
//        }
//
//        // Error message
//        error?.let { errorMessage ->
//            Text(
//                text = errorMessage,
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//
//        // User list
//        if (!loading && error == null) {
//            if (users.isEmpty()) {
//                Text(
//                    text = "No users found",
//                    modifier = Modifier.padding(16.dp)
//                )
//            } else {
//                LazyColumn {
//                    users.forEach { user ->
//                        item {
//                            UserItem(user = user)
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun UserItem(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Name: ${user.username}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Email: ${user.email}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun UserListScreen(userViewModel: RemoteUserViewModel = viewModel()) {
    val users by userViewModel.usersList.collectAsState()
    val isLoading by userViewModel.loading.collectAsState()
    val error by userViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        userViewModel.selectAllUsers()
    }

    val user = User(
        uid = "hgfhgf"
    )

    Button(
        onClick = {
            userViewModel.addUser(user)
        }
    ) {
        Text(text = "Clikc")
    }

//    Column(modifier = Modifier.fillMaxSize().padding(16.dp) .verticalScroll(rememberScrollState())) {
//        if (isLoading) {
//            CircularProgressIndicator()
//        } else if (error != null) {
//            Text("Error: $error", color = Color.Red)
//        } else {
//                users.forEach { user ->
//                    Text(text = user.username.toString())
//                    Text(text = user.uid.toString())
//                }
//        }
//    }
}


//@Composable
//fun CheckEmail(
//    viewModel: LocalUserViewModel = hiltViewModel()
//) {
//    val emailExists by viewModel.emailExists.collectAsState()
//    val loading by viewModel.localLoading.collectAsState()
//    val error by viewModel.localLoading.collectAsState()
//    val success by viewModel.success.collectAsState()
//
//    var emailInput by remember { mutableStateOf("") }
//    var checkTrigger by remember { mutableStateOf(0) }
//
//    // Validate and check email when input changes or button is clicked
//    LaunchedEffect(emailInput, checkTrigger) {
//        if (emailInput.isNotEmpty() && isValidEmail(emailInput)) {
//            viewModel.checkUserEmail(emailInput)
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .background(Color.White, shape = RoundedCornerShape(16.dp)),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        // Email input field
//        OutlinedTextField(
//            value = emailInput,
//            onValueChange = { emailInput = it },
//            label = { Text("Enter Email") },
//            modifier = Modifier.fillMaxWidth(),
//            isError = emailInput.isNotEmpty() && !isValidEmail(emailInput)
//        )
//
//        if (emailInput.isNotEmpty() && !isValidEmail(emailInput)) {
//            Text(
//                text = "Invalid email format",
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.padding(8.dp)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Button to check email
//        Button(
//            onClick = { checkTrigger++ },
//            modifier = Modifier.fillMaxWidth(),
//            enabled = !loading && emailInput.isNotEmpty()
//        ) {
//            Text("Next")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Loading indicator
//        if (loading) {
//            CircularProgressIndicator()
//        }
//
//        // Error message
//        error?.let { errorMessage ->
//            Text(
//                text = errorMessage,
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.padding(8.dp)
//            )
//        }
//
//        // Success message
//        success?.let { successMessage ->
//            Text(
//                text = successMessage,
//                color = MaterialTheme.colorScheme.primary,
//                modifier = Modifier.padding(8.dp)
//            )
//        }
//
//        // Email existence result and navigation
//        emailExists?.let { exists ->
//            if (exists) {
//                Text(
//                    text = "Email is already registered",
//                    color = MaterialTheme.colorScheme.error,
//                    modifier = Modifier.padding(8.dp)
//                )
//                Button(
//                    onClick = { },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Proceed to Login")
//                }
//            } else {
//                Text(
//                    text = "Email is available",
//                    color = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier.padding(8.dp)
//                )
//                Button(
//                    onClick = {  },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Proceed to Registration")
//                }
//            }
//        }
//    }
//}

fun isValidEmail(email: String): Boolean {
    return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun InsertUser(){
    val viewModel : LocalUserViewModel = hiltViewModel()

    val user = User(
        username = "yrtytryr",
        email = "tan@gmail.com",
        password = "",
//        photoUrl = "grtryry"
    )

    Button(
        onClick = {
            viewModel.insertNewUser(user)
        }
    ) {
        Text(
            text = "Next"
        )
    }
}


//
//@Composable
//fun UserScreen(
//    viewModel: LocalUserViewModel = hiltViewModel()
//) {
//    val users by viewModel.selectAllUser().collectAsState(initial = emptyList())
//    val loading by viewModel.loading.collectAsState()
//    val error by viewModel.error.collectAsState()
//    val success by viewModel.success.collectAsState()
//    val scrollState = rememberScrollState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Users",
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        error?.let {
//            Text(
//                text = it,
//                color = Color.Red,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            )
//        }
//
//        success?.let {
//            Text(
//                text = it,
//                color = Color.Green,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            )
//        }
//
//        if (loading) {
//            CircularProgressIndicator(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            )
//        } else {
//            if (users.isEmpty()) {
//                Text(
//                    text = "No users found",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    textAlign = TextAlign.Center
//                )
//            } else {
//                Column(
//                    modifier = Modifier
//                        .verticalScroll(scrollState)
//                ) {
//                    users.forEach { user ->
//                        Text(
//                            text = "Email: ${user.email ?: "N/A"}, Name: ${user.displayName ?: "Anonymous"}",
//                            modifier = Modifier.padding(8.dp)
//                        )
//                    }
//                }
//            }
//        }
//
//        // Button to insert a test user
//        Button(
//            onClick = {
//                val newUser = User(
//                    uid = UUID.randomUUID().toString(),
//                    email = "test${System.currentTimeMillis()}@example.com",
//                    displayName = "Test User",
//                    photoUrl = null
//                )
////                viewModel.checkAndInsertUsers(listOf(newUser))
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text("Insert Test User")
//        }
//    }
//}

@Preview(showBackground = true, widthDp = 360, heightDp = 806)
@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    val viewModel: AuthViewModel = viewModel()

    val naviagtionRoute = viewModel.newNavigationCommand.collectAsState()

    Log.e("Login","it is came from the home" + naviagtionRoute.value.toString())

    val startDestination = if(naviagtionRoute.value) AppScreen.HomeGraph.route else AppScreen.AuthGraph.route

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = AppScreen.fromRoute(
        backStackEntry?.destination?.route
    )

    Scaffold(
        topBar = {
            if (currentScreen.hasTopBar) {
                TitleBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() },
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
        },
        bottomBar = {
            if (currentScreen.hasBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        AppNavigation(navController, startDestination, Modifier.padding(innerPadding), viewModel)
        Log.e("MyTag", "This is an error log message")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = currentScreen.route.replace(Regex("([a-z])([A-Z])"), "$1 $2"),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = -20.dp)
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier.height(74.dp),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = navigateUp,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomAppBar(
        modifier = Modifier.height(90.dp),
        containerColor = Color.Transparent
    ) {
        val navItems = BottomNavigation.entries.toTypedArray()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.parent?.route
        var selectedIndex by remember { mutableIntStateOf(0) }

        LaunchedEffect(currentRoute) {
            selectedIndex = navItems.indexOfFirst { it.navigation.route == currentRoute }
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .height(64.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                navItems.forEachIndexed { index, item ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable(
                                enabled = index != selectedIndex,
                            ) {
                                selectedIndex = index
                                navController.navigate(item.navigation.route) {
                                    popUpTo(AppScreen.Home.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                            .size(64.dp)
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            tint = if (index == selectedIndex) AppTheme.colorScheme.primary else Color.Gray,
                            modifier = Modifier
                                .size(32.dp)
                        )
                        Text(
                            text = item.title,
                            style = AppTheme.typography.smallRegular,
                            color = if (index == selectedIndex) AppTheme.colorScheme.primary else Color.Gray,
                        )
                    }
                }
            }
        }
    }
}
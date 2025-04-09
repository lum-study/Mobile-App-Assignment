package com.bookblitzpremium.upcomingproject

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.navigation.AppNavigation
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme

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

//@Composable
//fun StartOrderScreen(
//    quantityOptions: List<Pair<Int, Int>>,
//    onNextButtonClicked: () -> Unit,
//    modifier: Modifier = Modifier
//){
//}
//

//
//
//Button(
//modifier = Modifier.weight(1f),
//enabled = selectedValue.isNotEmpty(),
//onClick = onNextButtonClicked
//) {
//    Text(stringResource(R.string.next))
//}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme{
                App()
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 806)
@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    val startDestination = AppScreen.AuthGraph.route
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.AuthGraph.route
    )

    Scaffold(
        topBar = {
            if (currentScreen.hasTopBar) {
                TitleBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )
            }
        },
        bottomBar = {
            if(currentScreen.hasBottomBar){
            BottomAppBar(
                containerColor = Color(0xFFC4C4C4),
                contentColor = Color.White,
                modifier = Modifier.height(65.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                    color = Color.Black
                )
            }
            }
        }
    ) { innerPadding ->
        AppNavigation(navController, startDestination, Modifier.padding(innerPadding))
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
        title = { Text(
            text = currentScreen.name.replace(Regex("([a-z])([A-Z])"), "$1 $2"),
            fontWeight = FontWeight.Bold
        ) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}



@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SplitScreen() {
    val activity = LocalContext.current as? Activity ?: return
    val windowSizeClass = calculateWindowSizeClass(activity)
    val isTablet = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded &&
            windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium

    var showBye by remember { mutableStateOf(false) } // Toggle state

    if (isTablet) {
        // 🟢 Tablet Layout: Splitting screen into two halves
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // 🟥 Left Side (Static Red Background)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Text("Static Side", color = Color.White, style = MaterialTheme.typography.headlineLarge)
            }

            // 🟦 Right Side (Dynamic Component Switcher)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (showBye) Bye() else Hello()

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { showBye = !showBye },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text(if (showBye) "Switch to Hello" else "Switch to Bye")
                    }
                }
            }
        }
    } else {
        // 🔵 Default Phone Layout (Stacked)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showBye) Bye() else Hello()

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { showBye = !showBye },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(if (showBye) "Switch to Hello" else "Switch to Bye")
            }
        }
    }
}

@Composable
fun Hello() {
    Text(
        text = "Hello!",
        color = Color.White,
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
fun Bye() {
    Text(
        text = "Bye!",
        color = Color.White,
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
fun MySimpleLayout() {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight()
            .paint(painterResource(id = R.drawable.hotel_images), contentScale = ContentScale.Crop)
    ) {
        Text(
            text = "fdgfghfgh",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}




@Composable
fun DraggableDividerExample() {

    var topHeight by remember { mutableStateOf(300.dp) } // Initial height of top section
    val minHeight = 50.dp // Minimum height limit for the top section
    val maxHeight = 500.dp // Maximum height before the divider stops
    val dragSpeedFactor = 0.3f // 🔹 Slows down movement (smaller = slower)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(topHeight)
                .background(Color.Red)
        ) {
            Text(
                text = "Top Section",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Draggable Divider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp) // Thickness of the divider
                .background(Color.Gray)
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        val scaledDelta = delta * dragSpeedFactor // 🔹 Reduce movement speed
                        val newHeight = (topHeight.value + scaledDelta).dp
                        topHeight = newHeight.coerceIn(minHeight, maxHeight) // Restrict movement
                    }
                )
        )

        // Bottom Section
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        ) {
            Text(
                text = "Bottom Section",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}



//phone flat
//width:expanded
//height Compact

//phone
//width: compact
//height  expand


//Tablet flat
//width: expanded
//height expanded

//Tablet
//width:  expanded
//height  expanded
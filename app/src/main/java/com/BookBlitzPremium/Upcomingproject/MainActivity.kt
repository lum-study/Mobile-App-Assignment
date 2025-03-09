package com.BookBlitzPremium.Upcomingproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.BookBlitzPremium.Upcomingproject.ui.theme.AppTheme
import com.BookBlitzPremium.Upcomingproject.ui.theme.LocalAppColorSchema
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.xr.scenecore.Dimensions
import com.BookBlitzPremium.Upcomingproject.login.PageLoaded


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme{


//                PageLoaded("Explorer the world","Lets start here")
//                TypographyPreview()
                FilledButtonExample(onClick = { println("Button clicked!") })
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        color = AppTheme.colorScheme.primary,
        style = AppTheme.typography.labelMedium
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        PageLoaded("Explorer the world","Lets start here")
    }
}



@Preview(showBackground = true)
@Composable
fun TypographyPreview() {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Display Large (57sp) - The Quick Brown Fox",
            style = AppTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Headline Large (32sp) - The Quick Brown Fox",
            style = AppTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Title Large (22sp) - The Quick Brown Fox",
            style = AppTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Body Large (16sp) - The Quick Brown Fox",
            style = AppTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Label Medium (14sp) - The Quick Brown Fox",
            style = AppTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Label Small (12sp) - The Quick Brown Fox",
            style = AppTheme.typography.labelSmall
        )
    }
}

@Composable
fun FilledButtonExample(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(), // Matches the full width of the parent
        contentPadding = PaddingValues(10.dp), // Sets padding inside the button
        shape = RoundedCornerShape(28.dp), // Matches the rounded corners in the screenshot
        colors = ButtonDefaults.buttonColors(

        )
    ) {
        Text(
            text = "Filled",
            fontSize = 16.sp // Matches the approximate size in the screenshot
        )
    }
}
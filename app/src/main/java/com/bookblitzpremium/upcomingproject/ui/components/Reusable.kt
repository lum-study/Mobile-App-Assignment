package com.bookblitzpremium.upcomingproject.ui.components

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme

val videoUri = Uri.parse("android.resource://com.bookblitzpremium.upcomingproject/raw/entry_video")

@Composable
fun TextHeader(text:String){
    Text(
        text = text,
        style = AppTheme.typography.largeBold,
        fontSize = 33.sp,
    )
}

@Composable
fun TextEmailSent(){
    Text(
        text = stringResource(R.string.verification_code_message),
        style = AppTheme.typography.bodyLarge,
        color = Color.LightGray
    )
}

@Composable
fun LineOver(){
    Box(
        modifier = Modifier
            .padding(vertical = 16.dp)
        , contentAlignment = Alignment.Center
    ){
        Divider(
            color = Color.Gray, // Customize color
            thickness = 1.dp,   // Set thickness
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text( text = stringResource(R.string.or_login_with),
            style = AppTheme.typography.labelMedium,
            modifier = Modifier
                .padding(horizontal = 152.dp)
        )
    }
}

@Composable
fun ButtonHeader(textResId: Int, valueHorizontal : Dp, onNextButtonClicked: () -> Unit, ){
    Button(
        onClick = onNextButtonClicked,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black, // Background color
            contentColor = Color.White    // Text color (optional)
        ),
        border = BorderStroke(2.dp, Color.Black), // Black border
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = valueHorizontal)
    ) {
        Text(
            text = stringResource(id = textResId),
            style = AppTheme.typography.labelMedium
        )
    }
}

@Composable
fun ClickableFun(
    text: String,
    onClick: () -> Unit
) {
    ClickableText(
        text = AnnotatedString(text),
        style = TextStyle(
            color = Color.Black,
            textDecoration = TextDecoration.Underline
        ),
        onClick = { onClick() } // Navigate when clicked
    )
}


@Composable
fun SignInWithGoogle( valueHorizontal: Dp){
    Button(
        onClick = { /* Handle login */ },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black, // Background color
            contentColor = Color.White    // Text color (optional)
        ),
        border = BorderStroke(2.dp, Color.Black), // Black border
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = valueHorizontal)
    ) {

        Text(
            text = stringResource(R.string.sign_in_with),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(end = 8.dp)
        )

        Image(
            painter = painterResource( id = R.drawable.google_symbol),
            contentDescription = "Google",
            modifier = Modifier.size(16.dp)
        )
    }
}


@Composable
fun HotelHeader(showBackButton: Int) {
    val heightSize: Modifier = if (showBackButton == 1) Modifier.fillMaxSize() else Modifier.height(500.dp)
    val pictureSize: Modifier = if (showBackButton == 1) Modifier.fillMaxSize() else Modifier.height(550.dp)

    Box(
        modifier = Modifier
            .background(Color.White)
    ){
        Image(
            painter = painterResource(id = R.drawable.hotel_images),
            contentDescription = "Hotel Image",
            contentScale = ContentScale.Crop, // ✅ Crops and fills the box
            modifier = Modifier.fillMaxSize() // ✅ Fills the Box
        )

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .padding(16.dp)
                .size(32.dp)
                .clickable { /* Handle back navigation */ }
                .align(Alignment.TopStart)
        )

    }
}

@Composable
fun HeaderDetails( textResId: Int , offsetX :Dp,modifier: Modifier ){
    Text(
        text = stringResource(id = textResId),
        color = Color.Black,
        style = AppTheme.typography.largeBold,
        modifier = Modifier
            .padding(top = 8.dp)
            .offset(x = offsetX)
    )
}

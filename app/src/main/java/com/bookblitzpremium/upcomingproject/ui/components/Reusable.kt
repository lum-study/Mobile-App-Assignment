package com.bookblitzpremium.upcomingproject.ui.components

import android.net.Uri
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.ViewModel.UserLogin
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme

val videoUri = Uri.parse("android.resource://com.bookblitzpremium.upcomingproject/raw/entry_video")

@Preview(showBackground = true)
@Composable
fun PreviewDialog(){
//    HotelFullNotication()
    CustomDialog(onDismissRequest = {}, onNextClick = {})
}

@Composable
fun CustomDialog(
    onDismissRequest: () -> Unit,
    onNextClick: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Column(
            modifier = Modifier
                .height(450.dp)
                .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
                .background(Color.White), // Make sure to set a background inside Dialog
            verticalArrangement = Arrangement.spacedBy(24.dp)
            ,horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.hotel_full),
                contentDescription = null,
            )

            Text(
                text = "Payment Successful",
                style = AppTheme.typography.largeBold,
            )

            Text(
                text = "Your payment is completed\nCheck your inbox",
                style = AppTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

//            ButtonHeader(
//                textResId = R.string.next_button,
//                valueHorizontal = 16.dp,
//                userLogin = UserLogin(),
//                email = email,
//                password = password
//            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotication(){
    NoticationToUser()
}


@Composable
fun NoticationToUser(){
     Box(
        modifier = Modifier
            .padding(16.dp)
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
     ){
         Row(
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(start = 16.dp),
             horizontalArrangement = Arrangement.spacedBy(16.dp),
             verticalAlignment = Alignment.CenterVertically
         ){
            Icon(
                imageVector = Icons.Filled.Image,
                contentDescription = null
            )

             Text(
                 text = "Donot forget to Checkout",
                 style = AppTheme.typography.labelMedium
             )
         }
     }
}

@Composable
fun TextHeader(text:String){
    Text(
        text = text,
        style = AppTheme.typography.largeBold,
    )
}

@Composable
fun TextEmailSent(){
    Text(
        text = stringResource(R.string.verification_code_message),
        style = AppTheme.typography.labelMedium,
        color = Color.LightGray
    )
}

@Composable
fun LineOver() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left Divider
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .weight(1f) // Takes available space on the left
                .padding(horizontal = 8.dp)
        )

        // Text in the center
        Text(
            text = "Or login with", // Replace with stringResource(R.string.or_login_with) if available
            style = TextStyle( // Fallback style if AppTheme is not available
                fontSize = 14.sp,
                color = Color(0xFF6B7280) // Gray color
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        // Right Divider
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .weight(1f) // Takes available space on the right
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun ButtonHeader(
    textResId: Int,
    valueHorizontal: Dp,
    userLogin: UserLogin,
    email: String,
    password: String, navContorller : NavController){
    Button(
        onClick = {
            userLogin.signup(email,password)
            navContorller.navigate(AppScreen.Login.route)
        },
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
fun SignInWithGoogle(valueHorizontal: Dp, viewModel: UserLogin, email: String, password: String){
    Button(
        onClick = {
            viewModel.login(email, password)
        },
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


@Composable
fun Notication(){

}

package com.bookblitzpremium.upcomingproject.ui.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme

@Preview(showBackground = true)
@Composable
fun PreviewDialog(){
//    HotelFullNotication()
     val navContoller = rememberNavController()
    CustomDialog(onDismissRequest = {}, onNextClick = {})
}

@Composable
fun CustomDialog(
    onDismissRequest: () -> Unit,
    onNextClick: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Column(
            modifier = Modifier
                .height(450.dp)
                .width(300.dp) // optional: define width for better shape
                .clip(RoundedCornerShape(16.dp)) // apply rounded corners
                .background(Color.White) // inner background
                .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(16.dp),
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

            ButtonHeader(
                textResId = R.string.next_button,
                valueHorizontal = 16.dp,
                onClick = {
                    onNextClick()
                }
            )

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
fun ButtonHeader(
    textResId: Int,
    valueHorizontal: Dp,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        border = BorderStroke(2.dp, Color.Black),
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
fun HeaderDetails( textResId: String , offsetX :Dp,modifier: Modifier ){
    Text(
        text = textResId,
        color = Color.Black,
        style = AppTheme.typography.largeBold,
        modifier = Modifier
            .padding(top = 8.dp)
            .offset(x = offsetX)
    )
}

data class LoginFormState(
    val email: String,
    val password: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Enter text",
    placeholder: String = "Type here...",
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    shape: RoundedCornerShape = RoundedCornerShape(0.dp),
    keyBoardType: KeyboardType = KeyboardType.Text,
    inputType: VisualTransformation = VisualTransformation.None,
    isEmailField: Boolean = false,
    modifier: Modifier = Modifier
) {
    var isError by remember { mutableStateOf(false) }

    fun validateEmail(email: String): Boolean {
        return email.contains("@") && email.isNotBlank()
    }

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
            if (isEmailField) {
                isError = !validateEmail(newValue)
            }
        },
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        leadingIcon = leadingIcon?.let {
            { Icon(imageVector = it, contentDescription = null, tint = Color.Gray) }
        },
        trailingIcon = trailingIcon?.let {
            {
                IconButton(onClick = {
                    onValueChange("")
                    isError = false
                }) {
                    Icon(imageVector = it, contentDescription = "Clear", tint = Color.Gray)
                }
            }
        },
        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isEmailField) KeyboardType.Email else keyBoardType,
            imeAction = ImeAction.Next
        ),
        visualTransformation = inputType,
        enabled = true,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = "Invalid email: Must contain '@'",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextFieldPassword(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Enter text",
    placeholder: String = "Type here...",
    leadingIcon: ImageVector? = null,
    shape: RoundedCornerShape = RoundedCornerShape(0.dp),
    keyBoardType: KeyboardType = KeyboardType.Password,
    modifier: Modifier = Modifier
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val visualTransformation = if (isPasswordVisible) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }

    val trailingIcon = if (isPasswordVisible) {
        Icons.Default.VisibilityOff
    } else {
        Icons.Default.Visibility
    }

    fun getPasswordErrorMessage(password: String): String? {
        if (password.length < 8) {
            return "Password must be at least 8 characters long"
        }
        return null
    }

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
            errorMessage = getPasswordErrorMessage(newValue)
            isError = errorMessage != null
        },
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        leadingIcon = leadingIcon?.let {
            { Icon(imageVector = it, contentDescription = null, tint = Color.Gray) }
        },
        trailingIcon = {
            IconButton(onClick = {
                isPasswordVisible = !isPasswordVisible
            }) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                    tint = Color.Gray
                )
            }
        },
        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyBoardType,
            imeAction = ImeAction.Done
        ),
        visualTransformation = visualTransformation,
        enabled = true,
        isError = isError,
        supportingText = {
            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
    )
}


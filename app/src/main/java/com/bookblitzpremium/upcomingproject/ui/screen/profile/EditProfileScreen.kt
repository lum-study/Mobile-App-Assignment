package com.bookblitzpremium.upcomingproject.ui.screen.profile

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AddCard
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.bookblitzpremium.upcomingproject.R

@Composable
fun EditProfileScreen() {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isTablet = windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT

    var username by remember { mutableStateOf("Emul_Ezep") }
    var email by remember { mutableStateOf("Emul.ezepboy33@gmail.com") }
    var phone by remember { mutableStateOf("081233334444") }
    var dob by remember { mutableStateOf("12-12-2012") }
    var address by remember { mutableStateOf("Canberra ACT 2601, Australia") }

    val fields = listOf(
        "Username" to username,
        "Email" to email,
        "Phone" to phone,
        "Date of Birth" to dob,
        "Address" to address
    )

    if (isTablet) {
        TabletLayout(fields, { username = it }, { email = it }, { phone = it }, { dob = it }, { address = it })
    } else {
        PhoneLayout(fields, { username = it }, { email = it }, { phone = it }, { dob = it }, { address = it })
    }
}

@Composable
private fun TabletLayout(
    fields: List<Pair<String, String>>,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onDobChange: (String) -> Unit,
    onAddressChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.size(40.dp))
            Text("Profile", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold), modifier = Modifier.padding(start = 32.dp))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f)) { NavigationOptions() }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp).padding(vertical = 8.dp), color = Color.LightGray)
            Column(modifier = Modifier.weight(2f).padding(start = 32.dp)) {
                ProfileImageSection()
                Spacer(modifier = Modifier.height(24.dp))
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    fields.forEach { (label, value) ->
                        ProfileField(label, value) {
                            when (label) {
                                "Username" -> onUsernameChange(it)
                                "Email" -> onEmailChange(it)
                                "Phone" -> onPhoneChange(it)
                                "Date of Birth" -> onDobChange(it)
                                "Address" -> onAddressChange(it)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {}, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(48.dp)) {
                    Text("Save Changes", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun PhoneLayout(
    fields: List<Pair<String, String>>,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onDobChange: (String) -> Unit,
    onAddressChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
        Spacer(modifier = Modifier.height(12.dp))
        ProfileImageSection(true)
        Spacer(modifier = Modifier.height(24.dp))
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                fields.forEach { (label, value) ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(label, style = MaterialTheme.typography.bodyMedium)
                        BasicTextField(value, {
                            when (label) {
                                "Username" -> onUsernameChange(it)
                                "Email" -> onEmailChange(it)
                                "Phone" -> onPhoneChange(it)
                                "Date of Birth" -> onDobChange(it)
                                "Address" -> onAddressChange(it)
                            }
                        }, textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.End), modifier = Modifier.weight(1f).padding(start = 16.dp))
                    }
                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray, modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
        Button(onClick = {}, modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 32.dp).height(48.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3), contentColor = Color.White)) {
            Text("Save Changes", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
        }
    }
}

@Composable
private fun NavigationOptions() {
    Column(modifier = Modifier.fillMaxWidth().padding(start = 16.dp)) {
        Spacer(modifier = Modifier.height(24.dp))
        val navOptions = listOf(
            "Your Profile" to Icons.Outlined.Person,
            "Payment Methods" to Icons.Outlined.AddCard,
            "My Orders" to Icons.Outlined.Task,
            "Ratings" to Icons.Default.Star,
            "Log Out" to Icons.Default.Logout
        )
        navOptions.forEach { (option, iconRes) ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 12.dp).clickable {}) {
                Image(imageVector = iconRes, contentDescription = null, modifier = Modifier.size(28.dp).padding(end = 8.dp))
                Text(option, style = MaterialTheme.typography.titleMedium)
            }
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray, modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}

@Composable
private fun ProfileImageSection(isPhone: Boolean = false) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Image(painter = painterResource(R.drawable.beach2), contentDescription = "Profile Image", modifier = Modifier.size(if (isPhone) 120.dp else 200.dp).clip(RoundedCornerShape(100.dp)))
        Surface(color = Color.Cyan, shape = CircleShape, modifier = Modifier.size(if (isPhone) 25.dp else 50.dp).align(Alignment.BottomCenter)) {
            Image(imageVector = Icons.Default.CameraAlt, contentDescription = "Camera", modifier = Modifier.size(if (isPhone) 40.dp else 60.dp).padding(if (isPhone) 4.dp else 6.dp))
        }
    }
}

@Composable
fun ProfileField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(label, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 32.dp))
            BasicTextField(value, onValueChange, textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.End), modifier = Modifier.widthIn(min = 200.dp).padding(end = 32.dp))
        }
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray, modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp))
    }
}

@Preview(showBackground = true, name = "Phone Portrait", device = "spec:width=411dp,height=891dp")
@Composable
fun PhonePortraitPreviewEditProfile() {
    EditProfileScreen()
}

@Preview(showBackground = true, name = "Tablet Portrait", device = "spec:width=800dp,height=1280dp")
@Composable
fun TabletPortraitPreviewEditProfile() {
    EditProfileScreen()
}

@Preview(showBackground = true, name = "Tablet Landscape", device = "spec:width=1280dp,height=800dp")
@Composable
fun TabletLandscapePreviewEditProfile() {
    EditProfileScreen()
}
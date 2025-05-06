package com.bookblitzpremium.upcomingproject.ui.screen.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AddCard
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.window.core.layout.WindowWidthSizeClass
import com.bookblitzpremium.upcomingproject.common.enums.Gender
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalUserViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteUserViewModel
import com.bookblitzpremium.upcomingproject.ui.components.Base64Image
import com.bookblitzpremium.upcomingproject.ui.components.CustomDatePickerDialog
import com.bookblitzpremium.upcomingproject.ui.components.uriToBase64
import com.bookblitzpremium.upcomingproject.ui.screen.booking.isValidPhoneNumber
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun EditProfileScreen() {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)
    var previousDeviceType by rememberSaveable { mutableStateOf(deviceType) }

    val remoteUserViewModel: RemoteUserViewModel = hiltViewModel()
    val localUserViewModel: LocalUserViewModel = hiltViewModel()
    val userID = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    var userInfo by remember { mutableStateOf<User?>(null) }

    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var dob by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var iconImage by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }

    val hasError by remoteUserViewModel.error.collectAsState()
    LaunchedEffect(userID) {
        if (deviceType == previousDeviceType) {
            userInfo = localUserViewModel.getUserByID(userID)
            if (userInfo != null) {
                username = userInfo!!.name
                email = userInfo!!.email
                phone = userInfo!!.phone
                dob = userInfo!!.dateOfBirth
                address = userInfo!!.address
                iconImage = userInfo!!.iconImage
                gender = userInfo!!.gender
            }
        }
        previousDeviceType = deviceType
    }

    val fields = listOf(
        "Username" to username,
        "Phone" to phone,
        "Date of Birth" to dob,
        "Address" to address
    )

    val context = LocalContext.current
    PhoneLayout(
        fields = fields,
        onUsernameChange = { username = it },
        onEmailChange = { email = it },
        onPhoneChange = { phone = it },
        onDobChange = { dob = it },
        onAddressChange = { address = it },
        onImageChange = { iconImage = it },
        iconImage = iconImage,
        gender = gender,
        onSaveClick = {
            if (!isValidPhoneNumber(phone)) {
                Toast.makeText(context, "Invalid phone number", Toast.LENGTH_SHORT).show()
                return@PhoneLayout
            }
            val user = User(
                id = userInfo!!.id,
                name = username,
                email = email,
                phone = phone,
                dateOfBirth = dob,
                address = address,
                password = userInfo!!.password,
                gender = userInfo!!.gender,
                iconImage = iconImage
            )
            remoteUserViewModel.updateUser(user)
            if (hasError == null) {
                localUserViewModel.addOrUpdateUser(user)
                Toast.makeText(context, "Profile is updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
            }
        }
    )
}

@Composable
private fun TabletLayout(
    fields: List<Pair<String, String>>,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onDobChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onImageChange: (String) -> Unit,
    gender: String,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(40.dp)
            )
            Text(
                "Profile",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 32.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f)) { NavigationOptions() }
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .padding(vertical = 8.dp),
                color = Color.LightGray
            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 32.dp)
            ) {
                ProfileImageSection(
                    iconImage = fields[0].second,
                    onImageChange = {
                        onImageChange(it)
                    },
                    gender = gender
                )
                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    fields.forEach { (label, value) ->
                        ProfileField(label, value) {
                            when (label) {
                                "Username" -> onUsernameChange(it)
                                "Phone" -> onPhoneChange(it)
                                "Date of Birth" -> onDobChange(it)
                                "Address" -> onAddressChange(it)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(48.dp)
                ) {
                    Text(
                        "Save Changes",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
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
    onAddressChange: (String) -> Unit,
    onImageChange: (String) -> Unit,
    iconImage: String,
    gender: String,
    onSaveClick: () -> Unit,
) {
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        ProfileImageSection(
            isPhone = true,
            iconImage = iconImage,
            gender = gender,
            onImageChange = { onImageChange(it) })
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                fields.forEach { (label, value) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(label, style = MaterialTheme.typography.bodyMedium)
                        if (label == "Date of Birth") {
                            Text(
                                text = value,
                                style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.End),
                                modifier = Modifier
                                    .clickable { showDatePicker = true }
                                    .weight(1f)
                                    .padding(start = 16.dp)
                            )
                        } else {
                            BasicTextField(
                                value = value,
                                onValueChange = {
                                    when (label) {
                                        "Username" -> onUsernameChange(it)
                                        "Phone" -> onPhoneChange(it)
                                        "Address" -> onAddressChange(it)
                                    }
                                },
                                textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.End),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 16.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = if (label == "Phone") KeyboardType.Phone else KeyboardType.Text
                                ),
                            )
                        }
                    }
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
        Button(
            onClick = { onSaveClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 32.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3),
                contentColor = Color.White
            )
        ) {
            Text(
                "Save Changes",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        if (showDatePicker) {
            val context = LocalContext.current
            CustomDatePickerDialog(
                onDateChange = {
                    if (it.isBefore(LocalDate.now())) {
                        onDobChange(it.format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
                        showDatePicker = false
                    } else {
                        Toast.makeText(context, "Invalid Date", Toast.LENGTH_SHORT).show()
                    }
                },
            )
        }
    }
}

@Composable
private fun NavigationOptions() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        val navOptions = listOf(
            "Your Profile" to Icons.Outlined.Person,
            "Payment Methods" to Icons.Outlined.AddCard,
            "My Orders" to Icons.Outlined.Task,
            "Ratings" to Icons.Default.Star,
            "Log Out" to Icons.Default.Logout
        )
        navOptions.forEach { (option, iconRes) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .clickable {}) {
                Image(
                    imageVector = iconRes,
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 8.dp)
                )
                Text(option, style = MaterialTheme.typography.titleMedium)
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun ProfileImageSection(
    isPhone: Boolean = false,
    iconImage: String,
    gender: String,
    onImageChange: (String) -> Unit,
) {
    val context = LocalContext.current
    var base64String by rememberSaveable { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        base64String = uri?.let { uriToBase64(context, it) }
    }

    LaunchedEffect(base64String) {
        if (base64String != null) {
            println(base64String!!.length)
            if (base64String!!.length < 50000 && base64String!!.isNotEmpty()) {
                onImageChange(base64String ?: "")
            } else {
                Toast.makeText(
                    context,
                    "Image too large. Please choose a smaller one.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        if (iconImage.isNotEmpty()) {
            Base64Image(
                base64String = iconImage,
                modifier = Modifier
                    .size(if (isPhone) 120.dp else 200.dp)
                    .clip(RoundedCornerShape(100.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                imageVector = if (gender == Gender.Male.title) Icons.Default.Male else Icons.Default.Female,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(if (isPhone) 120.dp else 200.dp)
                    .clip(RoundedCornerShape(100.dp))
            )
        }
        Surface(
            color = Color.Cyan,
            shape = CircleShape,
            modifier = Modifier
                .size(if (isPhone) 25.dp else 50.dp)
                .align(Alignment.BottomCenter)
        ) {
            Image(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Camera",
                modifier = Modifier
                    .size(if (isPhone) 40.dp else 60.dp)
                    .padding(if (isPhone) 4.dp else 6.dp)
                    .clickable {
                        launcher.launch("image/*")
                    }
            )
        }
    }
}

@Composable
fun ProfileField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 32.dp)
            )
            BasicTextField(
                value,
                onValueChange,
                textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.End),
                modifier = Modifier
                    .widthIn(min = 200.dp)
                    .padding(end = 32.dp)
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
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

@Preview(
    showBackground = true,
    name = "Tablet Landscape",
    device = "spec:width=1280dp,height=800dp"
)
@Composable
fun TabletLandscapePreviewEditProfile() {
    EditProfileScreen()
}
package com.bookblitzpremium.upcomingproject.UserPage

//@Composable
//fun TextHeader(text:String){
//    Text(
//        text = text,
//        style = AppTheme.typography.largeBold,
//        fontSize = 33.sp,
//    )
//}
//
//@Composable
//fun TextEmailSent(){
//    Text(
//        text = stringResource(R.string.verification_code_message),
//        style = AppTheme.typography.bodyLarge,
//        color = Color.LightGray
//    )
//}
//
//val videoUri = Uri.parse("android.resource://com.BookBlitzPremium.Upcomingproject/raw/entry_video")
//
//@Composable
//fun LineOver(){
//    Box(
//        modifier = Modifier
//            .padding(vertical = 16.dp)
//        , contentAlignment = Alignment.Center
//    ){
//        Divider(
//            color = Color.Gray, // Customize color
//            thickness = 1.dp,   // Set thickness
//            modifier = Modifier.padding(vertical = 8.dp)
//        )
//        Text( text = stringResource(R.string.or_login_with),
//            style = AppTheme.typography.labelMedium,
//            modifier = Modifier
//                .padding(horizontal = 152.dp)
//        )
//    }
//}
//
//@Composable
//fun ButtonHeader(textResId: Int, valueHorizontal : Dp,onNextButtonClicked: () -> Unit, ){
//    Button(
//        onClick = onNextButtonClicked,
//        colors = ButtonDefaults.buttonColors(
//            containerColor = Color.Black, // Background color
//            contentColor = Color.White    // Text color (optional)
//        ),
//        border = BorderStroke(2.dp, Color.Black), // Black border
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp, horizontal = valueHorizontal)
//    ) {
//        Text(
//            text = stringResource(id = textResId),
//            style = AppTheme.typography.labelMedium
//        )
//    }
//}
//
//@Composable
//fun ClickableFun(
//    text: String,
//    onClick: () -> Unit
//) {
//    ClickableText(
//        text = AnnotatedString(text),
//        style = TextStyle(
//            color = Color.Black,
//            textDecoration = TextDecoration.Underline
//        ),
//        onClick = { onClick() } // Navigate when clicked
//    )
//}
//
//
//@Composable
//fun SignInWithGoogle( valueHorizontal: Dp ){
//    Button(
//        onClick = { /* Handle login */ },
//        colors = ButtonDefaults.buttonColors(
//            containerColor = Color.Black, // Background color
//            contentColor = Color.White    // Text color (optional)
//        ),
//        border = BorderStroke(2.dp, Color.Black), // Black border
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = valueHorizontal)
//    ) {
//
//        Text(
//            text = stringResource(R.string.sign_in_with),
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .padding(end = 8.dp)
//        )
//
//        Image(
//            painter = painterResource( id = R.drawable.google_symbol),
//            contentDescription = "Google",
//            modifier = Modifier.size(16.dp)
//        )
//    }
//}

//@Composable
//fun RegristerPage(showToggleToTablet: Boolean,  onNextButtonClicked: () -> Unit, navController: NavController ){
//
//    val valueHorizontal = if (showToggleToTablet) 46.dp else 16.dp
//    val maxSizeAvailable = if (showToggleToTablet) 0.4f else 1f
//    val offsetValueX = if (showToggleToTablet) 620.dp else 0.dp
//    val offsetValueY = if (showToggleToTablet) 120.dp else 200.dp
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White.copy(alpha = 0.3f)) // Adjust opacity here
//    ) {
//
//        VideoPlayer(
//            videoUri = videoUri,
//            modifier = Modifier
//                .fillMaxSize()
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth(maxSizeAvailable) // 40% width for a "column" effect
//                .padding(horizontal = 28.dp)
//                .offset(x = offsetValueX),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center // Center content vertically
//        ){
//
//            TextHeader( stringResource(R.string.register_as_a_account) )
//
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp) // Automatically spaces elements
//            ) {
//                CustomTextField(
//                    value = "",
//                    onValueChange = { },
//                    label = "Username",
//                    placeholder = "Enter your Username",
//                    leadingIcon = Icons.Default.Person,
//                    trailingIcon = Icons.Default.Clear,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = valueHorizontal, vertical = 12.dp)
//                )
//
//                CustomTextField(
//                    value = "",
//                    onValueChange = { },
//                    label = "Password",
//                    placeholder = stringResource(R.string.enter_your_password),
//                    leadingIcon = Icons.Default.Lock,
//                    trailingIcon = Icons.Default.Clear,
//                    keyBoardType = KeyboardType.Password,
//                    inputType = PasswordVisualTransformation(),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = valueHorizontal, vertical = 12.dp)
//                )
//
//                CustomTextField(
//                    value = "",
//                    onValueChange = { },
//                    label = "Confirm Password",
//                    placeholder = "Enter your Confirm Password",
//                    leadingIcon = Icons.Default.Lock,
//                    trailingIcon = Icons.Default.Clear,
//                    keyBoardType = KeyboardType.Password,
//                    inputType = PasswordVisualTransformation(),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = valueHorizontal, vertical = 12.dp)
//                )
//            }
//
//
//
////            ButtonHeader(R.string.regrister_text, valueHorizontal)
//            LineOver()
//
//            SignInWithGoogle(valueHorizontal)
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp, horizontal = valueHorizontal),
//                horizontalArrangement = Arrangement.Center
//            ) {
////                ClickableText(
////                    text = AnnotatedString(),
////                    style = TextStyle(
////                        color = Color.Black,
////                        textDecoration = TextDecoration.Underline
////                    ),
////                    onClick = { /* Navigate to Login Screen */ }
////                )
//
//                ClickableFun(
//                    text = stringResource(R.string.already_have_an_account_login),
//                    onClick = { navController.navigate(TravelScreen.LOGIN.name) } // ✅ Wrap in a lambda
//                )
//
//            }
//
//        }
//    }
//}

//@Composable
//fun ForgetPassword(
//    showToggleToTablet: Boolean,
//    onNextButtonClicked: () -> Unit
//){
//    val valueHorizontal = if (showToggleToTablet) 46.dp else 16.dp
//    val maxSizeAvailable = if (showToggleToTablet) 0.4f else 1f
//    val offsetValueX = if (showToggleToTablet) 620.dp else 0.dp
//    val offsetValueY = if (showToggleToTablet) 120.dp else 200.dp
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White.copy(alpha = 0.3f)) // Adjust opacity here
//    ) {
//
//        VideoPlayer(
//            videoUri = videoUri,
//            modifier = Modifier
//                .fillMaxSize()
//
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth(maxSizeAvailable) // 40% width for a "column" effect
//                .padding(horizontal = 28.dp)
//                .offset(x = offsetValueX),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center // Center content vertically
//        ) {
//
//            TextHeader( stringResource(R.string.forget_password) )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            TextEmailSent()
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            CustomTextField(
//                value = "",
//                onValueChange = { },
//                label = "Emails",
//                placeholder = "Enter your Emails",
//                leadingIcon = Icons.Default.Email,
//                trailingIcon = Icons.Default.Clear,
//                keyBoardType = KeyboardType.Email,  // ✅ Correctly passing KeyboardType
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = valueHorizontal, vertical = 16.dp)
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
////            ButtonHeader( textResId = R.string.login , valueHorizontal )
//
////            Spacer(modifier = Modifier.height(30.dp))
//
////            Divider(color = Color.Gray, thickness = 1.dp)
//
////            Text(
////                text = "Or login with",
////                style = AppTheme.typography.labelMedium,
////                modifier = Modifier
////                    .padding(vertical = 16.dp)
////            )
//
//            ButtonHeader( R.string.login, valueHorizontal,onNextButtonClicked = onNextButtonClicked )
//        }
//    }
//}

//@Composable
//fun ChangePassword(showToggleToTablet: Boolean){
//
//    val valueHorizontal = if (showToggleToTablet) 46.dp else 16.dp
//    val maxSizeAvailable = if (showToggleToTablet) 0.4f else 1f
//    val offsetValueX = if (showToggleToTablet) 620.dp else 0.dp
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White.copy(alpha = 0.3f)) // Adjust opacity here
//    ) {
//
//        VideoPlayer(
//            videoUri = videoUri,
//            modifier = Modifier
//                .fillMaxSize()
//
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth(maxSizeAvailable) // 40% width for a "column" effect
//                .padding(horizontal = 28.dp)
//                .offset(x = offsetValueX),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center // Center content vertically
//        ) {
//
//            TextHeader(stringResource(R.string.change_password))
//
////            Row(
////                modifier = Modifier
////                    .padding(bottom = 30.dp),
////                horizontalArrangement = Arrangement.Center
////            ) {
////
////            }
//
//            TextEmailSent()
//
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth(0.98f)
//                    .padding(horizontal = 16.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp) // Automatically spaces elements
//            ){
//                CustomTextField(
//                    value = "",
//                    onValueChange = { },
//                    label = "Emails",
//                    placeholder = "Enter your Emails",
//                    leadingIcon = Icons.Default.Email,
//                    trailingIcon = Icons.Default.Clear,
//                    keyBoardType = KeyboardType.Email,  // ✅ Correctly passing KeyboardType
//                    inputType = VisualTransformation.None,  // ✅ No transformation
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = valueHorizontal, vertical = 16.dp)
//                )
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                CustomTextField(
//                    value = "",
//                    onValueChange = { },
//                    label = "Comfirm Passwords",
//                    placeholder = "Enter your Comfirm Passwords",
//                    leadingIcon = Icons.Default.Lock,
//                    trailingIcon = Icons.Default.Clear,
//                    keyBoardType = KeyboardType.Password,  // ✅ Correctly passing KeyboardType
//                    inputType = PasswordVisualTransformation(),  // ✅ No transformation
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = valueHorizontal, vertical = 16.dp)
//                )
//            }
//
////            ButtonHeader(R.string.change, valueHorizontal)
//
////            Spacer(modifier = Modifier.height(30.dp))
//
////            Divider(color = Color.Gray, thickness = 1.dp)
//
////            Text(
////                text = "Or login with",
////                style = AppTheme.typography.labelMedium,
////                modifier = Modifier
////                    .padding(vertical = 16.dp)
////            )
//        }
//    }
//}

//@Composable
//fun OTPpage(showToggleToTablet: Boolean){
//
//    val valueHorizontal = if (showToggleToTablet) 46.dp else 16.dp
//    val maxSizeAvailable = if (showToggleToTablet) 0.4f else 1f
//    val offsetValueX = if (showToggleToTablet) 620.dp else 0.dp
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//
//        VideoPlayer(
//            videoUri = videoUri,
//            modifier = Modifier
//                .fillMaxSize()
//
//        )
//
//        // 📝 Login UI - Positioned above the video
//        Column(
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth(maxSizeAvailable) // 40% width for a "column" effect
//                .padding(horizontal = 28.dp)
//                .offset(x = offsetValueX),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center // Center content vertically
//        ) {
//            Text(
//                text = "OTP Verification",
//                style = AppTheme.typography.expandBold,
//                modifier = Modifier
//                    .padding(bottom = 30.dp)
//                    .align(Alignment.CenterHorizontally)
//            )
//
//            Row (
//                modifier = Modifier
//                    .padding(vertical = 50.dp),
//                horizontalArrangement = Arrangement.spacedBy(16.dp)
//
//            ){
////                Column(
////                    horizontalAlignment = Arrangement.spacedBy(16.dp),
////                    modifier = Modifier
////                ){
//                    repeat(4) { // Creates 4 Box items
//                        Box(
//                            modifier = Modifier
//                                .size(48.dp) // Circle size
//                                .border(width = 1.dp, color = Color.Black, RoundedCornerShape(8.dp))
//                                .background(Color.LightGray) // Circular background
//                                .padding(12.dp), // Inner padding
//                            contentAlignment = Alignment.Center
//                        ) {
//                            // Content inside the Box (e.g., Icon, Text, etc.)
//                        }
//                    }
////                }
//            }
//
//            Row(
//                modifier = Modifier
//                    .padding(vertical = 16.dp)
//                    .padding(start = 24.dp)
//            ) {
//                Text(
//                    text = "Didn’t receive code? "
//                )
//
//                Text(
//                    text = "Resend",
//                    color = Color.Blue, // Set text color to blue
//                    style = TextStyle(
//                        textDecoration = TextDecoration.Underline // Underline the text
//                    ),
//                    modifier = Modifier.clickable {
//                        // Handle click action here
//                    }
//                )
//
//            }
//
////            ButtonHeader(R.string.verify, valueHorizontal , )
//        }
//    }
//}



//@Preview(showBackground = false, name = "Phone Mode")
//@Composable
//fun LoginPagePhonePreview() {
//    LoginPage(showToggleToTablet = false)
//}
//
//// Preview for tablet mode
//@Preview(showBackground = true, widthDp = 800, heightDp = 600, name = "Tablet Mode")
//@Composable
//fun LoginPageTabletPreview() {
//    LoginPage(showToggleToTablet = true)
//}


//@SuppressLint("ContextCastToActivity")
//@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//@Composable
//fun LoginPageView(wording: String, wording2: String) {
//    val activity = LocalContext.current as? Activity
//    AppTheme {
//        if (activity != null) {
//            val windowSizeClass = calculateWindowSizeClass(activity = activity)
//            when {
//                windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded &&
//                        windowSizeClass.heightSizeClass == WindowHeightSizeClass.Expanded -> {
//
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(Color(0xFFffe2c9))
//                    ) {
//                        val videoUri = Uri.parse("android.resource://com.BookBlitzPremium.Upcomingproject/raw/entry_video")
//
//                        Column(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .align(Alignment.TopStart) // Align to top-left of Box
//                                .background(Color.Black)
//                        ) {
//                            VideoPlayer(
//                                videoUri = videoUri,
//                                modifier = Modifier
//                                    .fillMaxSize()       // Fill the Column (half width, full height)
//                                    .clip(RoundedCornerShape(16.dp))
//                            )
//                        }
//                    }
//                }
//
//                windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact &&
//                        windowSizeClass.heightSizeClass == WindowHeightSizeClass.Expanded -> {
//
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(Color(0xFFffe2c9))
//                    ) {
//                        // Background Image
////                        Image(
////                            painter = painterResource(id = R.drawable.green_mountain),
////                            contentDescription = "Mountain landscape background",
////                            contentScale = ContentScale.Crop,
////                            modifier = Modifier
////                                .fillMaxSize()
////                                .align(Alignment.BottomCenter)
////                        )
//
//                        val videoUri = Uri.parse("android.resource://com.BookBlitzPremium.Upcomingproject.login/raw/entry_video")
//
//                        Column(
//                            modifier = Modifier
//                                .fillMaxSize()
//                        ) {
//                            VideoPlayer(
//                                videoUri =videoUri,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .fillMaxHeight() // Half screen height
//                                    .clip(RoundedCornerShape(16.dp))
//                            )
//                        }
//
//                        Column(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(Color.Transparent),
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Row(
//                                modifier = Modifier
//                                    .padding(top = 40.dp, start = 36.dp)
//                                    .width(280.dp)
//                            ) {
//                                Text(
//                                    text = wording,
//                                    style = AppTheme.typography.headlineLarge,
//                                    modifier = Modifier.padding(top = 160.dp)
//                                )
//                            }
//
//                            Spacer(modifier = Modifier.height(40.dp))
//
//                            // Icon Row
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(top = 80.dp),
//                                horizontalArrangement = Arrangement.Center
//                            ) {
//                                Box(
//                                    modifier = Modifier
//                                        .size(48.dp) // Circle size
//                                        .clip(CircleShape) // Make it circular
//                                        .background(Color.LightGray) // Background color
//                                        .padding(12.dp),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Default.ArrowForward,
//                                        contentDescription = "Arrow icon",
//                                        modifier = Modifier.size(24.dp),
//                                        tint = Color.Black
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//
//                else -> {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(Color(0xFFffe2c9))
//                    ) {
//                        // Background Image
//                        Image(
//                            painter = painterResource(id = R.drawable.green_mountain),
//                            contentDescription = "Mountain landscape background",
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .align(Alignment.BottomCenter)
//                        )
//
//                        Column(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(Color.Transparent),
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Row(
//                                modifier = Modifier
//                                    .padding(top = 40.dp, start = 36.dp)
//                                    .width(280.dp)
//                            ) {
//                                Text(
//                                    text = wording,
//                                    style = AppTheme.typography.headlineLarge,
//                                    modifier = Modifier.padding(top = 160.dp)
//                                )
//                            }
//
//                            Spacer(modifier = Modifier.height(40.dp))
//
//                            // Icon Row
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(top = 80.dp),
//                                horizontalArrangement = Arrangement.Center
//                            ) {
//                                Box(
//                                    modifier = Modifier
//                                        .size(48.dp) // Circle size
//                                        .clip(CircleShape) // Make it circular
//                                        .background(Color.LightGray) // Background color
//                                        .padding(12.dp),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Default.ArrowForward,
//                                        contentDescription = "Arrow icon",
//                                        modifier = Modifier.size(24.dp),
//                                        tint = Color.Black
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text(text = "Activity is null - Preview mode or invalid context")
//            }
//        }
//    }
//}
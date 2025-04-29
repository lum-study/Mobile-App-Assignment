//package com.bookblitzpremium.upcomingproject.ui.screen.hotel
//
//import android.util.Log
//import android.widget.Toast
//import androidx.compose.animation.core.animateDpAsState
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.gestures.Orientation
//import androidx.compose.foundation.gestures.draggable
//import androidx.compose.foundation.gestures.rememberDraggableState
//import androidx.compose.foundation.horizontalScroll
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Arrangement.spacedBy
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.foundation.layout.wrapContentSize
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AcUnit
//import androidx.compose.material.icons.filled.Accessibility
//import androidx.compose.material.icons.filled.CalendarToday
//import androidx.compose.material.icons.filled.ChildCare
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material.icons.filled.FitnessCenter
//import androidx.compose.material.icons.filled.FreeBreakfast
//import androidx.compose.material.icons.filled.Hotel
//import androidx.compose.material.icons.filled.LocalParking
//import androidx.compose.material.icons.filled.LocationOn
//import androidx.compose.material.icons.filled.People
//import androidx.compose.material.icons.filled.Pets
//import androidx.compose.material.icons.filled.Pool
//import androidx.compose.material.icons.filled.SmokeFree
//import androidx.compose.material.icons.filled.Wifi
//import androidx.compose.material3.AssistChip
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Divider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.bookblitzpremium.upcomingproject.R
//import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
//import com.bookblitzpremium.upcomingproject.ui.components.HeaderDetails
//import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextDecoration
//import androidx.compose.ui.window.Dialog
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.compose.rememberNavController
//import com.bookblitzpremium.upcomingproject.StarRating
//import com.bookblitzpremium.upcomingproject.common.enums.Feature
//import com.bookblitzpremium.upcomingproject.common.enums.PaymentMethod
//import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
//import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
//import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
//import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
//import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalRatingViewModel
//import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteHotelBookingViewModel
//import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemotePaymentViewModel
//import com.bookblitzpremium.upcomingproject.data.model.Calendar
//import com.bookblitzpremium.upcomingproject.ui.components.TeamMemberDropdown
//import com.bookblitzpremium.upcomingproject.ui.components.TripPackageBookingDialog
//import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
//import com.bookblitzpremium.upcomingproject.ui.screen.booking.CalendarView
//import com.bookblitzpremium.upcomingproject.ui.screen.booking.DetailsSection
//import com.bookblitzpremium.upcomingproject.ui.screen.booking.generateHotelDescription
//import com.bookblitzpremium.upcomingproject.ui.screen.payment.PaymentOptionScreen
//import com.google.firebase.auth.FirebaseAuth
//import kotlinx.coroutines.launch
//import java.net.URLEncoder
//import java.time.LocalDate
//import kotlin.toString
//
//
//
//
//
//
//
//
//
//
//
//
//@Preview(name = "Mobile Layout", showBackground = true, widthDp = 500, heightDp = 1000)
//@Composable
//fun PreviewMobileLayout1() {
//    val navController = rememberNavController()
//    OverlappingContentTest(2, navController)
//}
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun OverlappingContentTest(
//    showBackButton: Int,
//    navController: NavController
//) {
//    var isExpanded by remember { mutableStateOf(true) } // 🔥 Toggle state
//    val offsetValue by animateDpAsState(
//        targetValue = if (isExpanded) 300.dp else 0.dp, // 🔥 Moves content up
//        animationSpec = tween(300)
//    )
//
//    val textOffset = 24.dp
//    val rangeBetweenLocation = when (showBackButton) {
//        1 -> 340.dp
//        2 -> 150.dp
//        else -> 500.dp
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        if (isExpanded) {
//            // ✅ Expanded: LazyColumn (Scrollable)
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                item {
//                    Image(
//                        painter = painterResource(id = R.drawable.hotel_images),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(800.dp)
//                    )
//
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(Color.White)
//                            .offset(y = -500.dp)
//                            .wrapContentSize()
//                            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
////                            .padding(top = 16.dp,bottom= 32.dp)
//                    ) {
//
//                        Column(
//                            modifier = Modifier
//                                .background(Color.White, RoundedCornerShape(32.dp))
//                        ) {
//                            Box(
//                                modifier = Modifier
//                                    .padding(top = 8.dp, bottom = 16.dp)
//                                    .size(width = 200.dp, height = 5.dp)
//                                    .background(Color.Gray, RoundedCornerShape(50))
//                                    .clickable { isExpanded = !isExpanded }
//                                    .align(Alignment.CenterHorizontally)
//                            )
//
//                            Row(
//                                modifier = Modifier.background(Color.White),
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                HeaderDetails("HotelName",textOffset, modifier = Modifier.padding(top = 12.dp))
//                                Spacer(modifier = Modifier.width(8.dp))
//
//                                Box(
//                                    modifier = Modifier
//                                        .offset(x = rangeBetweenLocation)
//                                        .size(48.dp)
//                                        .background(Color.LightGray, RoundedCornerShape(16.dp))
//                                        .clickable { /* Handle location click */ },
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Default.LocationOn,
//                                        contentDescription = "Location",
//                                        tint = Color.Black,
//                                        modifier = Modifier.size(28.dp)
//                                    )
//                                }
//                            }
//
//                            Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))
//
//                            Text(
//                                text = "211B Baker Street, London, England",
//                                color = Color.Gray,
//                                fontSize = 14.sp,
//                                modifier = Modifier.padding(horizontal = 16.dp)
//                            )
//
//                            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
//                                repeat(4) {
//                                    Text(text = "⭐", color = Color.Yellow, fontSize = 16.sp)
//                                }
//                                Text(
//                                    text = "4.5 - 1231 Reviews",
//                                    color = Color.Gray,
//                                    fontSize = 14.sp,
//                                    modifier = Modifier.padding(start = 4.dp)
//                                )
//                            }
//
//                            HotelDescriptionSection(showBackButton = 2, modifier = Modifier, description = "")
//                            HotelPreviewImages(showBackButton = 2, modifier = Modifier)
////                            HotelReviewsSection(showBackButton = 2, modifier = Modifier.height(500.dp))
//
//                            Button(
//                                onClick = { navController.navigate(AppScreen.BookingDate.route) },
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = Color.Blue,
//                                    contentColor = Color.White
//                                ),
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(8.dp)
//                            ) {
//                                Text(text = "Booking")
//                            }
//                        }
//                    }
//                }
//            }
//
//        } else {
//            // ✅ Collapsed: Box (Static, not scrollable)
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.Transparent)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.hotel_images),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(700.dp),
//                    )
//
//                    Column(
//                        modifier = Modifier
//                            .offset(y = -100.dp)
//                            .fillMaxSize()
//                    ){
//                        Column(
//                            modifier = Modifier
//                                .background(
//                                    Color.White,
//                                    RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
//                                ) // ✅ Rounded top corners
//                                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
//                                .fillMaxWidth()
//                                .fillMaxHeight()
//                                .wrapContentHeight(),
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ){
//                            Box(
//                                modifier = Modifier
//                                    .padding(top = 8.dp, bottom = 16.dp)
//                                    .size(width = 200.dp, height = 5.dp)
//                                    .background(Color.Gray, RoundedCornerShape(50))
//                                    .clickable { isExpanded = !isExpanded }
//                            )
//
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                                modifier = Modifier
//                                    .offset(x = -100.dp)
//                            ){
//                                HeaderDetails("Hotel Name", textOffset, modifier = Modifier)
//                                Spacer(modifier = Modifier.width(8.dp))
//
//                                Box(
//                                    modifier = Modifier
//                                        .offset(x = rangeBetweenLocation)
//                                        .size(48.dp)
//                                        .background(Color.LightGray, RoundedCornerShape(16.dp))
//                                        .clickable { /* Handle location click */ },
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Default.LocationOn,
//                                        contentDescription = "Location",
//                                        tint = Color.Black,
//                                        modifier = Modifier.size(28.dp)
//                                    )
//                                }
//                            }
//
//                            Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))
//
//                            Column(
//                                modifier = Modifier
//                                    .offset(x = -64.dp)
//                            ){
//                                Text(
//                                    text = "211B Baker Street, London, England",
//                                    color = Color.Gray,
//                                    fontSize = 14.sp,
//                                    modifier = Modifier.padding(horizontal = 16.dp)
//                                )
//
//                                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
//                                    repeat(4) {
//                                        Text(text = "⭐", color = Color.Yellow, fontSize = 16.sp)
//                                    }
//                                    Text(
//                                        text = "4.5 - 1231 Reviews",
//                                        color = Color.Gray,
//                                        fontSize = 14.sp,
//                                        modifier = Modifier.padding(start = 4.dp)
//                                    )
//                                }
//                            }
//
//                            Spacer(modifier = Modifier.height(100.dp))
//
//                            HotelDescriptionSection(showBackButton = 2, modifier = Modifier, description = "")
//
//
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//fun HotelScreen() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.LightGray) // Background for visualization
//    ) {
//        LazyColumn(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            item {
//                Box(modifier = Modifier.fillMaxWidth()) {
//                    // 🔹 Large Image
//                    Image(
//                        painter = painterResource(id = R.drawable.hotel_images),
//                        contentDescription = "Hotel Image",
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(800.dp) // Large image
//                    )
//
//                    // 🔹 White Box (Overlapping the Image)
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(Color.White)
//                            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
//                            .align(Alignment.BottomCenter) // ✅ Overlaps the image correctly
//                            .padding(top = 16.dp, bottom = 32.dp)
//                    ) {
//                        Spacer(modifier = Modifier.height(16.dp)) // 🔹 Push content down
//
//                        Text(
//                            text = "Hotel Details",
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.Bold,
//                            modifier = Modifier.padding(horizontal = 16.dp)
//                        )
//
//                        Button(
//                            onClick = { /* Navigate to booking */ },
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(8.dp)
//                        ) {
//                            Text(text = "Book Now")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//fun ProductDetailScreen() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFF6FAF5))
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Card(
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(24.dp),
//            elevation = CardDefaults.cardElevation(4.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.White)
//                    .padding(20.dp)
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Column {
//                        Text(
//                            text = "Eclipse V2",
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 20.sp
//                        )
//                        Row {
//                            Text(
//                                text = "$165.00",
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 18.sp,
//                                color = Color(0xFF2C6E49)
//                            )
//                            Spacer(modifier = Modifier.width(8.dp))
//                            Text(
//                                text = "$200.00",
//                                style = TextStyle(
//                                    textDecoration = TextDecoration.LineThrough
//                                ),
//                                color = Color.Gray,
//                                fontSize = 16.sp
//                            )
//                        }
//                    }
//                    Text(
//                        text = "ERO2542",
//                        color = Color.Gray,
//                        fontSize = 12.sp,
//                        modifier = Modifier
//                            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp))
//                            .padding(horizontal = 8.dp, vertical = 4.dp)
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Specs Row
//                Row(
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    SpecItem("Height:", "4 cm")
//                    SpecItem("Width:", "15 cm")
//                    SpecItem("Material", "Glass")
//                }
//
//                Spacer(modifier = Modifier.height(24.dp))
//
//                Button(
//                    onClick = { /* Add to cart logic */ },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp),
//                    shape = RoundedCornerShape(12.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C6E49))
//                ) {
//                    Text("Add to Cart", fontSize = 18.sp, color = Color.White)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun SpecItem(label: String, value: String) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Text(text = label, color = Color.Gray, fontSize = 14.sp)
//        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
//    }
//}
//
//
//
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewHotelScreen() {
//    HotelScreen()
//}
//
//

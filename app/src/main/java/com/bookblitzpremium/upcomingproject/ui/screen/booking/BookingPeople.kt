package com.bookblitzpremium.upcomingproject.ui.screen.booking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Traffic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.SelectingFigure



@Composable
fun PreviewBookingPeople(){
//    ResolveButtonUp()
}


@Composable
fun PeopleSelected(
    modifier: Modifier
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
            ){
                Text(
                    text = "Not Selected",
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(
                    text = "Ages below 13 ",
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 16.dp, end= 16.dp),
            ){
                Box(
                    modifier = Modifier
                        .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp)) // ✅ Border with rounded corners
                        .clip(RoundedCornerShape(8.dp)) // ✅ Clip to make sure content follows the rounded shape
                        .padding(8.dp) // Optional padding
                ) {
                    Icon(
                        imageVector = Icons.Filled.Remove,
                        contentDescription = "Remove",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "1",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 24.dp , vertical = 12.dp)
                )

                Box(
                    modifier = Modifier
                        .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp)) // ✅ Border with rounded corners
                        .clip(RoundedCornerShape(8.dp)) // ✅ Clip to make sure content follows the rounded shape
                        .padding(8.dp) // Optional padding
                ) {
                    Icon(
                        imageVector = Icons.Filled.Remove,
                        contentDescription = "Remove",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ){

            Column(
                modifier = Modifier
                    .width(200.dp)
                    .padding(start =  16.dp)
            ){
                Text(
                    text = "Children",
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(
                    text = "Ages below 13 ",
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Column(
                modifier = Modifier
                    .width(350.dp),
                verticalArrangement = Arrangement.Center,
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, end= 16.dp),
                ){
                    Box(
                        modifier = Modifier
                            .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp)) // ✅ Border with rounded corners
                            .clip(RoundedCornerShape(8.dp)) // ✅ Clip to make sure content follows the rounded shape
                            .padding(8.dp) // Optional padding
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = "Remove",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Text(
                        text = "1",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 24.dp , vertical = 12.dp)
                    )

                    Box(
                        modifier = Modifier
                            .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp)) // ✅ Border with rounded corners
                            .clip(RoundedCornerShape(8.dp)) // ✅ Clip to make sure content follows the rounded shape
                            .padding(8.dp) // Optional padding
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = "Remove",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ){

            Column(
                modifier = Modifier
                    .width(200.dp)
                    .padding(start =  16.dp)
            ){
                Text(
                    text = "Infant",
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(
                    text = "Ages below 13 ",
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Column(
                modifier = Modifier
                    .width(350.dp),
                verticalArrangement = Arrangement.Center,
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, end= 16.dp),
                ){
                    Box(
                        modifier = Modifier
                            .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp)) // ✅ Border with rounded corners
                            .clip(RoundedCornerShape(8.dp)) // ✅ Clip to make sure content follows the rounded shape
                            .padding(8.dp) // Optional padding
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = "Remove",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Text(
                        text = "1",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 24.dp , vertical = 12.dp)
                    )

                    Box(
                        modifier = Modifier
                            .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp)) // ✅ Border with rounded corners
                            .clip(RoundedCornerShape(8.dp)) // ✅ Clip to make sure content follows the rounded shape
                            .padding(8.dp) // Optional padding
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = "Remove",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun GuestSection(
    modifier: Modifier,
    navController: NavController
){
    Column(
        modifier = Modifier
    ) {
        Text(
            text = "Total Guest",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 12.dp)
        )

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // Equal height distribution
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp), // Adds space between children
                        horizontalAlignment = Alignment.CenterHorizontally // Centers items
                    ){
                        Icon(imageVector = Icons.Filled.Traffic, contentDescription = "Trafffic", tint = Color.Black, modifier = Modifier.size(24.dp))

                        Text(
                            text = "Adult",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(top = 8.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navController.navigate(AppScreen.Home.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black, // Ensure the background is visible
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Next")
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}


// add the number of people which is going to the trips
@Composable
fun BookingAmount(
    modifier: Modifier,
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        repeat(3){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                ){
                    Text(
                        text = "Not Selected",
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Text(
                        text = "Ages below 13 ",
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = 16.dp, end= 16.dp),
                ){
                    Box(
                        modifier = Modifier
                            .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp)) // ✅ Border with rounded corners
                            .clip(RoundedCornerShape(8.dp)) // ✅ Clip to make sure content follows the rounded shape
                            .padding(8.dp) // Optional padding
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = "Remove",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Text(
                        text = "1",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 24.dp , vertical = 12.dp)
                    )

                    Box(
                        modifier = Modifier
                            .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp)) // ✅ Border with rounded corners
                            .clip(RoundedCornerShape(8.dp)) // ✅ Clip to make sure content follows the rounded shape
                            .padding(8.dp) // Optional padding
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = "Remove",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        GuestSection(modifier = Modifier, navController = navController)
    }
}
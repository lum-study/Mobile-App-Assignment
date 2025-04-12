package com.bookblitzpremium.upcomingproject.ui.screen.booking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.SelectingFigure

@Composable
fun SelectedGuestResult(
    iconRes: Int,   // 🔥 Pass the image resource as an argument
    label: String,
    number:Int
) {
    Box(
        modifier = Modifier
            .width(IntrinsicSize.Min),  // ✅ Ensures only necessary width
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = label,
                modifier = Modifier.size(48.dp)
            )

            Text(
                text = number.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun TravelInfoCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFFF7043)) // Orange background
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_launcher_foreground), // Change to your icon resource
                contentDescription = "Travel Icon",
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = "Enjoy your trip!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Text(
                    text = "Have a safe and pleasant journey.",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
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
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()  // ✅ Takes up full width
                .padding(top = 16.dp, start = 50.dp)
                ,
            horizontalArrangement = Arrangement.spacedBy(60.dp) // ✅ Ensures proper spacing
        ) {
            SelectedGuestResult(R.drawable.adults, "Adults", 5)
            SelectedGuestResult(R.drawable.children,"Children", 5)
            SelectedGuestResult(R.drawable.infant, "Infant", 5)
        }


        Spacer(modifier = Modifier.weight(1f))

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
        , verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        TravelInfoCard()

        SelectPeopleLevel("Adult", "Ages 13 or above")

        SelectPeopleLevel("Children", "Ages 2 - 12")

        SelectPeopleLevel("Infant" , "Under ages 2")

        Spacer(modifier = Modifier.height(10.dp))

        GuestSection(modifier = Modifier, navController = navController)
    }
}

@Composable
fun SelectPeopleLevel(
    label1: String,
    label2:String,
){

    var count by remember { mutableIntStateOf(1) }

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
                text = label1,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = label2,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier
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
                        .clickable {
                            count --
                        }
                )
            }

            Text(
                text =  count.toString(),
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
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                        .clickable {
                            count ++
                        }
                )
            }
        }
    }
}
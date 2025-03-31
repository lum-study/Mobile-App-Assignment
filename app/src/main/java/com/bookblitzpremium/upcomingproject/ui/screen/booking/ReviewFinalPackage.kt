package com.bookblitzpremium.upcomingproject.ui.screen.booking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.TurnedInNot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen

//@Preview(showBackground = true , widthDp = 500 , heightDp = 1000)
//@Composable
//fun PreviewFinalPackage(){
//    ReviewFinalPackageSelected()
//}

@Composable
fun ReviewFinalPackageSelected(
    navController: NavController,
    modifier: Modifier
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){

        Spacer(modifier = Modifier.height(40.dp))
        StyledImage()


        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ){

            Text(
                text = "Kuala Lumput Trip",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier,

            ){
                repeat(3){
                    Text(
                        text = "⭐",
                        modifier = Modifier

                    )
                }

                Row(
                    modifier = Modifier
                        .padding(start = 24.dp)
                    , verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Location",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(24.dp)
                    )

                    Text(
                        text = "Malaysia Johor",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 12.dp)
                    )
                }
            }
        }

        // Legend
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start =  20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            LegendItem1(
                icon = Icons.Filled.CalendarToday,
                iconDescription = "Check In Icon",
                label = "CheckIn"
            )

            LegendItem1(
                icon = Icons.Filled.CalendarToday,
                iconDescription = "Check out Icon",
                label = " CheckOut"
            )

            LegendItem1(
                icon = Icons.Filled.TurnedInNot,
                iconDescription = "Ticket Icon",
                label = " Ticket"
            )

        }

        DetailsSection()

        Button(
            onClick = {
                navController.navigate(AppScreen.BookingDate.route)
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
    }
}




@Composable
fun LegendItem1(icon: ImageVector, iconDescription: String, label: String) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon, // Use the passed icon argument
                contentDescription = iconDescription, // Use the passed description argument
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = label, fontSize = 12.sp)
        }

        Column(
        ){
            Text(
                text = "Kuala Lumpur Trip",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DetailsSection(){
    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
    ){
        repeat(3){
            Row(
                horizontalArrangement = Arrangement.spacedBy(220.dp)
            ){
                Text(
                    text = "Adult x 3",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "$300",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(120.dp)
        ){
            Text(
                text = "Total Prices",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "$ 300",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun StyledImage() {
    Card(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp), // Rounded top corners
        elevation = CardDefaults.cardElevation(4.dp), // Slight shadow for depth
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp)) // Ensure proper clipping
    ) {
        Image(
            painter = painterResource(id = R.drawable.hotel_images),
            contentDescription = "Beach Resort",
            contentScale = ContentScale.Crop, // Ensures it fills the frame
            modifier = Modifier.fillMaxWidth()
        )
    }
}

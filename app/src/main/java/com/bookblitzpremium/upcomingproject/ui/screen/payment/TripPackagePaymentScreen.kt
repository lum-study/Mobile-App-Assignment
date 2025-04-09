package com.bookblitzpremium.upcomingproject.ui.screen.payment

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.PaymentMethod
import com.bookblitzpremium.upcomingproject.ui.components.CustomInputField
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme

@Preview(showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun TripPackagePaymentPreview() {
    TripPackagePaymentScreen(rememberNavController())
}

@Composable
fun TripPackagePaymentScreen(navController: NavHostController) {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA)) // Light background
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RadioButton()
            Spacer(modifier = Modifier.weight(1f))
            PriceDetailsSection()
            Spacer(modifier = Modifier.height(16.dp))
            PaymentButton()
        }
    }
}

@Composable
fun CardSection(cardType: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth(.9f)
            .height(150.dp)
    ) {
        Box (
            modifier = Modifier.fillMaxHeight()
        ){
            Image(
                painter = painterResource(id = R.drawable.mastercard_bg5),
                contentDescription = stringResource(R.string.card_background),
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
            )
            Column(
                modifier = Modifier.padding(
                    top = 8.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(text = cardType, style = AppTheme.typography.mediumSemiBold)
                    if (cardType == PaymentMethod.DebitCard.title || cardType == PaymentMethod.CreditCard.title) {
                        Image(
                            painter = painterResource(id = R.drawable.mastercard), // Use a valid image resource
                            contentDescription = stringResource(R.string.mastercard_icon),
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "$1300.00",
                    style = AppTheme.typography.largeBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (cardType == PaymentMethod.DebitCard.title || cardType == PaymentMethod.CreditCard.title) {
                    Text(text = "EXP", style = AppTheme.typography.smallSemiBold)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "2/20", style = AppTheme.typography.smallSemiBold)
                        Text(
                            text = "1254 6354 3218 2296",
                            style = AppTheme.typography.smallSemiBold
                        )
                    }
                }
            }

            if (cardType == PaymentMethod.EWallet.title ) {
                Box(modifier = Modifier
                    .fillMaxWidth(.3f)
                    .fillMaxHeight(.5f)
                    .align(Alignment.BottomEnd)
                    .background(Color.White, shape = RoundedCornerShape(topStart = 100.dp))
                    .padding(bottom = 10.dp, end = 10.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.tng_icon2),
                        contentDescription = stringResource(R.string.card_background),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(50.dp)
                            .align(Alignment.BottomEnd),
                    )
                }
            }

        }
    }
}

@Composable
fun PriceDetailsSection() {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Price Details", style = AppTheme.typography.mediumBold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "The price includes state taxes and administration fees",
                style = AppTheme.typography.smallRegular,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.total_price),
                    style = AppTheme.typography.mediumBold,
                    color = Color(0xFFFF5722)
                )
                Text(
                    text = "$1300",
                    style = AppTheme.typography.mediumBold,
                    color = Color(0xFFFF5722)
                )
            }
        }
    }
}

@Composable
fun PaymentButton() {
    Button(
        onClick = {},
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFFFC71E), Color(0xFFFF9800)) // Yellow to Orange
                )
            ),
        contentPadding = PaddingValues()
    ) {
        Text(
            text = "Continue to payment",
            style = AppTheme.typography.mediumBold,
        )
    }
}

@Composable
fun RadioButton() {
    var selectedOption by remember { mutableStateOf(PaymentMethod.DebitCard) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        listOf(
            PaymentMethod.DebitCard,
            PaymentMethod.CreditCard,
            PaymentMethod.EWallet
        ).forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedOption = option },
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = painterResource(option.icon),
                    contentDescription = stringResource(R.string.mastercard_icon),
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = option.title,
                        style = AppTheme.typography.mediumSemiBold,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    if (
                        (option == PaymentMethod.CreditCard && selectedOption == PaymentMethod.CreditCard) ||
                        (option == PaymentMethod.DebitCard && selectedOption == PaymentMethod.DebitCard)
                    )
                        Text(
                            text = "1234 5678 9012 ....",
                            style = AppTheme.typography.mediumNormal,
                            modifier = Modifier.fillMaxWidth(),
                        )
                }
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = { selectedOption = option },
                )
            }
            if (option == PaymentMethod.DebitCard && selectedOption == PaymentMethod.DebitCard) {
                CardSection(PaymentMethod.DebitCard.title)
            }
            if (option == PaymentMethod.CreditCard && selectedOption == PaymentMethod.CreditCard) {
                CardSection(PaymentMethod.CreditCard.title)
            }
//                    && selectedOption == PaymentMethod.EWallet
            if (option == PaymentMethod.EWallet ) {
                CardSection(PaymentMethod.EWallet.title)
            }
        }
    }
}
package com.bookblitzpremium.upcomingproject.ui.screen.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.PaymentMethod
import com.bookblitzpremium.upcomingproject.ui.screen.booking.isValidPhoneNumber
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme

@Preview(showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun TripPackagePaymentPreview() {
    TripPackagePaymentScreen(rememberNavController())
}

@Composable
fun TripPackagePaymentScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    AppTheme {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PaymentOptionScreen()
            Spacer(modifier = Modifier.height(32.dp))
            PriceDetailsSection(1300.00, "", 10)
            Spacer(modifier = Modifier.height(16.dp))
            PaymentButton()
        }
    }
}

@Composable
fun PriceDetailsSection(totalAmount: Double, tripPackageName: String, totalQuantity: Int) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Price Summary", style = AppTheme.typography.mediumBold)
            Text(
                text = "The price includes state taxes and administration fees",
                style = AppTheme.typography.smallRegular,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tripPackageName,
                    style = AppTheme.typography.mediumBold.copy(lineHeight = 20.sp),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "x $totalQuantity",
                    style = AppTheme.typography.mediumBold,
                    modifier = Modifier.weight(.5f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(
                        R.string.price,
                        if (totalQuantity > 0) (totalAmount / totalQuantity).toFloat() else 0f
                    ),
                    style = AppTheme.typography.mediumBold,
                )
            }
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
                    text = stringResource(R.string.price, totalAmount),
                    style = AppTheme.typography.mediumBold,
                    color = Color(0xFFFF5722)
                )
            }
        }
    }
}

@Composable
fun PaymentButton(onclick: () -> Unit = {}, enabled: Boolean = true) {
    Button(
        onClick = { onclick() },
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
        contentPadding = PaddingValues(),
        enabled = enabled
    ) {
        Text(
            text = "Continue to payment",
            style = AppTheme.typography.mediumBold,
        )
    }
}

@Composable
fun PaymentOptionScreen(
    selectedPaymentMethod: PaymentMethod = PaymentMethod.DebitCard,
    onPaymentMethodChange: (PaymentMethod) -> Unit = {},
    cardNumber: String = "xxxx xxxx xxxx xxxx",
    onCardNumberChange: (String) -> Unit = {},
) {

    Column(
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
                    .clickable { onPaymentMethodChange(option) },
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
                }
                RadioButton(
                    selected = (option == selectedPaymentMethod),
                    onClick = { onPaymentMethodChange(option) },
                )
            }
            if (option == PaymentMethod.DebitCard && selectedPaymentMethod == PaymentMethod.DebitCard) {
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { onCardNumberChange(if (it.length <= 16) it else cardNumber) },
                    label = {
                        Text(
                            text = "Card number",
                            style = AppTheme.typography.smallRegular
                        )
                    },
                    placeholder = {
                        Text(
                            text = "0000 0000 0000 0000",
                            style = AppTheme.typography.smallRegular
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(.8f),
                    isError = cardNumber.length != 16,
                )
                if (cardNumber.length != 16) {
                    Text(
                        text = "Card number must be exactly 16 digits",
                        color = AppTheme.colorScheme.error,
                        style = AppTheme.typography.smallRegular,
                    )
                }
            }
            if (option == PaymentMethod.CreditCard && selectedPaymentMethod == PaymentMethod.CreditCard) {
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { onCardNumberChange(if (it.length <= 16) it else cardNumber) },
                    label = {
                        Text(
                            text = "Card number",
                            style = AppTheme.typography.smallRegular
                        )
                    },
                    placeholder = {
                        Text(
                            text = "0000 0000 0000 0000",
                            style = AppTheme.typography.smallRegular
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(.8f),
                    isError = cardNumber.length != 16,
                )
                if (cardNumber.length != 16) {
                    Text(
                        text = "Card number must be exactly 16 digits",
                        color = AppTheme.colorScheme.error,
                        style = AppTheme.typography.smallRegular,
                    )
                }
            }
            if (option == PaymentMethod.EWallet && selectedPaymentMethod == PaymentMethod.EWallet) {
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { onCardNumberChange(if (it.length < 12) it else cardNumber) },
                    label = {
                        Text(
                            text = "Phone number",
                            style = AppTheme.typography.smallRegular
                        )
                    },
                    placeholder = {
                        Text(
                            text = "012 345 6789",
                            style = AppTheme.typography.smallRegular
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(.8f),
                    isError = !isValidPhoneNumber(cardNumber, selectedPaymentMethod),
                )
                if (!isValidPhoneNumber(cardNumber, selectedPaymentMethod)) {
                    Text(
                        text = "Invalid phone number",
                        color = AppTheme.colorScheme.error,
                        style = AppTheme.typography.smallRegular,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

        }

    }
}

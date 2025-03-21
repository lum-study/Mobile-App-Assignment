package com.BookBlitzPremium.Upcomingproject.Screen.Payment

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.BookBlitzPremium.Upcomingproject.Enum.PaymentMethod
import com.BookBlitzPremium.Upcomingproject.Model.Transaction
import com.BookBlitzPremium.Upcomingproject.Model.TripPackagePrice
import com.BookBlitzPremium.Upcomingproject.R
import com.BookBlitzPremium.Upcomingproject.ui.theme.AppTheme

@SuppressLint("DefaultLocale")
@Preview(showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun ReceiptScreen() {
    val transaction = Transaction(
        transactionID = "324837548",
        paymentMethod = PaymentMethod.DebitCard,
        cardNumber = "0000 0000 0000 0000",
        currency = "Ringgit Malaysia",
        reference = "HX15EFQ65",
        transactionDate = "31-03-2025",
    )

    val packagePrice = TripPackagePrice(
        packages = 3120,
        hotel = 500,
        transport = 1400,
        extras = 132,
        discount = 0.1f,
    )

    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp, bottom = 8.dp, start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.total_amount),
                        style = AppTheme.typography.mediumSemiBold
                    )
                    Text(
                        text = String.format(
                            "RM%.2f",
                            (packagePrice.packages + packagePrice.extras + packagePrice.hotel + packagePrice.transport) * (1 - packagePrice.discount)
                        ),
                        style = AppTheme.typography.largeSemiBold
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.malaysia_flag),
                    contentDescription = stringResource(R.string.country_image),
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            HorizontalDivider()

            Text(
                text = stringResource(R.string.transaction_details),
                style = AppTheme.typography.mediumSemiBold
            )

            Card(
                colors = CardDefaults.cardColors(Color.LightGray)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(12.dp)
                ) {
                    InformationTextRow(
                        title = stringResource(R.string.transaction_id),
                        data = transaction.transactionID,
                        modifier = Modifier.fillMaxWidth()
                    )
                    InformationTextRow(
                        title = stringResource(R.string.payment_method),
                        data = transaction.paymentMethod.title,
                        modifier = Modifier.fillMaxWidth()
                    )
                    InformationTextRow(
                        title = stringResource(R.string.card_number),
                        data = transaction.cardNumber,
                        modifier = Modifier.fillMaxWidth()
                    )
                    InformationTextRow(
                        title = stringResource(R.string.currency),
                        data = transaction.currency,
                        modifier = Modifier.fillMaxWidth()
                    )
                    InformationTextRow(
                        title = stringResource(R.string.reference),
                        data = transaction.reference,
                        modifier = Modifier.fillMaxWidth()
                    )
                    InformationTextRow(
                        title = stringResource(R.string.transaction_date),
                        data = transaction.transactionDate,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Text(
                text = stringResource(R.string.total_amount_details),
                style = AppTheme.typography.mediumSemiBold,
                modifier = Modifier.padding(top = 8.dp)
            )
            Card(
                colors = CardDefaults.cardColors(Color.LightGray)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(12.dp)
                ) {
                    InformationTextRow(
                        title = stringResource(R.string.packages),
                        data = packagePrice.packages.toString(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    InformationTextRow(
                        title = stringResource(R.string.transport),
                        data = packagePrice.transport.toString(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    InformationTextRow(
                        title = stringResource(R.string.hotel),
                        data = packagePrice.hotel.toString(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    InformationTextRow(
                        title = stringResource(R.string.bag_and_extras),
                        data = packagePrice.extras.toString(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    InformationTextRow(
                        title = stringResource(R.string.discount),
                        data = String.format("%.0f%%", (packagePrice.discount * 100)),
                        modifier = Modifier.fillMaxWidth()
                    )
                    HorizontalDivider()
                    InformationTextRow(
                        title = stringResource(R.string.total_amount),
                        data = String.format(
                            "%.2f",
                            (packagePrice.packages + packagePrice.extras + packagePrice.hotel + packagePrice.transport) * (1 - packagePrice.discount)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 12.dp, end = 12.dp),
            ) {
                Text(
                    text = stringResource(R.string.transaction_completed)
                )
            }
        }
    }
}

@Composable
fun InformationTextRow(title: String, data: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = AppTheme.typography.mediumNormal,
            color = Color.Black
        )
        Text(
            text = data,
            style = AppTheme.typography.mediumNormal,
            color = Color.Black
        )
    }
}
package com.bookblitzpremium.upcomingproject.ui.screen.booking

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.BookingStatus
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.common.enums.PaymentMethod
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TPBooking
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteTPBookingViewModel
import com.bookblitzpremium.upcomingproject.ui.components.NotificationService
import com.bookblitzpremium.upcomingproject.ui.components.TripPackageBookingDialog
import com.bookblitzpremium.upcomingproject.ui.screen.payment.PaymentButton
import com.bookblitzpremium.upcomingproject.ui.screen.payment.PaymentOptionScreen
import com.bookblitzpremium.upcomingproject.ui.screen.payment.PriceDetailsSection
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate

@Composable
fun TripPackageBookingScreen(
    navController: NavHostController,
    tripPackageID: String,
    tripPackageName: String,
    tripPackagePrice: String,
    availableSlots: Int,
) {
    val context = LocalContext.current
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)

    val remoteTPBookingViewModel: RemoteTPBookingViewModel = hiltViewModel()
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userID = currentUser?.uid

    //Booking
    var childQuantity by rememberSaveable { mutableIntStateOf(0) }
    var adultQuantity by rememberSaveable { mutableIntStateOf(1) }

    //Payment
    val totalAmount = tripPackagePrice.toDouble() * (childQuantity + adultQuantity)
    var paymentMethod by rememberSaveable { mutableStateOf(PaymentMethod.DebitCard) }
    var cardNumber by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(paymentMethod) {
        cardNumber = ""
    }
    //Loading
    val isLoading by remoteTPBookingViewModel.loading.collectAsState()
    val hasError by remoteTPBookingViewModel.error.collectAsState()


    //Handle Alert Dialog
    var showDialog by rememberSaveable { mutableStateOf(false) }

    AppTheme {
        when (deviceType) {
            DeviceType.MobilePortrait -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Number of packages",
                            style = AppTheme.typography.mediumBold,
                        )
                        TripPackageCount(
                            label = "Adult",
                            description = "18+ years",
                            quantity = adultQuantity,
                            onDecrement = { if (it >= 0) adultQuantity = it },
                            onIncrement = { adultQuantity = it }
                        )
                        TripPackageCount(
                            label = "Children",
                            description = "Under 18 years",
                            quantity = childQuantity,
                            onDecrement = { if (it >= 0) childQuantity = it },
                            onIncrement = { childQuantity = it }
                        )
                    }
                    HorizontalDivider()
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Payment method",
                            style = AppTheme.typography.mediumBold,
                        )
                        PaymentOptionScreen(
                            selectedPaymentMethod = paymentMethod,
                            onPaymentMethodChange = {
                                paymentMethod = it
                            },
                            cardNumber = cardNumber,
                            onCardNumberChange = {
                                cardNumber = it.filter { it.isDigit() }
                            },
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        PriceDetailsSection(
                            totalAmount,
                            tripPackageName,
                            childQuantity + adultQuantity
                        )
                        PaymentButton(
                            onclick = {
                                showDialog = !showDialog
                                if (userID != null && childQuantity + adultQuantity <= availableSlots) {
                                    val payment = Payment(
                                        createDate = LocalDate.now().toString(),
                                        totalAmount = totalAmount,
                                        paymentMethod = paymentMethod.title,
                                        cardNumber = cardNumber,
                                        currency = "Ringgit Malaysia",
                                        userID = userID
                                    )
                                    val booking = TPBooking(
                                        purchaseCount = childQuantity + adultQuantity,
                                        paymentID = "",
                                        tripPackageID = tripPackageID,
                                        userID = userID,
                                        status = BookingStatus.Confirmed.title
                                    )
                                    remoteTPBookingViewModel.addPaymentAndBooking(
                                        payment = payment,
                                        tpBooking = booking,
                                        context = context
                                    )
                                }
                            },
                            enabled = !showDialog && (isValidCardNumber(
                                cardNumber,
                                paymentMethod
                            ) || isValidPhoneNumber(
                                cardNumber,
                                paymentMethod
                            )) && childQuantity + adultQuantity > 0
                        )
                    }
                }
            }

            DeviceType.TabletLandscape -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 16.dp, horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Number of packages",
                                style = AppTheme.typography.mediumBold,
                            )
                            TripPackageCount(
                                label = "Adult",
                                description = "18+ years",
                                quantity = adultQuantity,
                                onDecrement = { if (it >= 0) adultQuantity = it },
                                onIncrement = { adultQuantity = it }
                            )
                            TripPackageCount(
                                label = "Children",
                                description = "Under 18 years",
                                quantity = childQuantity,
                                onDecrement = { if (it >= 0) childQuantity = it },
                                onIncrement = { childQuantity = it }
                            )
                        }
                        VerticalDivider()
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 16.dp, horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Payment method",
                                style = AppTheme.typography.mediumBold,
                            )
                            PaymentOptionScreen(
                                selectedPaymentMethod = paymentMethod,
                                onPaymentMethodChange = {
                                    paymentMethod = it
                                },
                                cardNumber = cardNumber,
                                onCardNumberChange = {
                                    cardNumber = it.filter { it.isDigit() }
                                },
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        PriceDetailsSection(
                            totalAmount = totalAmount,
                            tripPackageName = tripPackageName,
                            totalQuantity = childQuantity + adultQuantity
                        )
                        PaymentButton(
                            onclick = {
                                showDialog = !showDialog
                                if (userID != null && childQuantity + adultQuantity <= availableSlots) {
                                    val payment = Payment(
                                        createDate = LocalDate.now().toString(),
                                        totalAmount = totalAmount,
                                        paymentMethod = paymentMethod.title,
                                        cardNumber = cardNumber,
                                        currency = "Ringgit Malaysia",
                                        userID = userID
                                    )
                                    val booking = TPBooking(
                                        purchaseCount = childQuantity + adultQuantity,
                                        paymentID = "",
                                        tripPackageID = tripPackageID,
                                        userID = userID,
                                        status = BookingStatus.Confirmed.title
                                    )
                                    remoteTPBookingViewModel.addPaymentAndBooking(
                                        payment = payment,
                                        tpBooking = booking,
                                        context = context
                                    )
                                }
                            },
                            enabled = !showDialog && (isValidCardNumber(
                                cardNumber,
                                paymentMethod
                            ) || isValidPhoneNumber(
                                cardNumber,
                                paymentMethod
                            )) && childQuantity + adultQuantity > 0
                        )
                    }
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp, vertical = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Number of packages",
                            style = AppTheme.typography.mediumBold,
                        )
                        TripPackageCount(
                            label = "Adult",
                            description = "18+ years",
                            quantity = adultQuantity,
                            onDecrement = { if (it >= 0) adultQuantity = it },
                            onIncrement = { adultQuantity = it }
                        )
                        TripPackageCount(
                            label = "Children",
                            description = "Under 18 years",
                            quantity = childQuantity,
                            onDecrement = { if (it >= 0) childQuantity = it },
                            onIncrement = { childQuantity = it }
                        )
                    }
                    HorizontalDivider()
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Payment method",
                            style = AppTheme.typography.mediumBold,
                        )
                        PaymentOptionScreen(
                            selectedPaymentMethod = paymentMethod,
                            onPaymentMethodChange = {
                                paymentMethod = it
                            },
                            cardNumber = cardNumber,
                            onCardNumberChange = {
                                cardNumber = it.filter { it.isDigit() }
                            },
                        )
                    }
                    Spacer(modifier = Modifier.height(150.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        PriceDetailsSection(
                            totalAmount,
                            tripPackageName,
                            childQuantity + adultQuantity
                        )
                        PaymentButton(
                            onclick = {
                                showDialog = !showDialog
                                if (userID != null && childQuantity + adultQuantity <= availableSlots) {
                                    val payment = Payment(
                                        createDate = LocalDate.now().toString(),
                                        totalAmount = totalAmount,
                                        paymentMethod = paymentMethod.title,
                                        cardNumber = cardNumber,
                                        currency = "Ringgit Malaysia",
                                        userID = userID
                                    )
                                    val booking = TPBooking(
                                        purchaseCount = childQuantity + adultQuantity,
                                        paymentID = "",
                                        tripPackageID = tripPackageID,
                                        userID = userID,
                                        status = BookingStatus.Confirmed.title
                                    )
                                    remoteTPBookingViewModel.addPaymentAndBooking(
                                        payment = payment,
                                        tpBooking = booking,
                                        context = context
                                    )
                                }
                            },
                            enabled = !showDialog && (isValidCardNumber(
                                cardNumber,
                                paymentMethod
                            ) || isValidPhoneNumber(
                                cardNumber,
                                paymentMethod
                            )) && childQuantity + adultQuantity > 0
                        )
                    }
                }
            }
        }

        if (showDialog) {
            if (childQuantity + adultQuantity <= availableSlots) {
                TripPackageBookingDialog(
                    modifier = Modifier
                        .height(300.dp)
                        .width(300.dp),
                    hasError = hasError ?: "",
                    isLoading = isLoading,
                    onHomeButtonClick = {
                        showDialog = it
                        navController.navigate(AppScreen.Home.route) {
                            popUpTo(AppScreen.Home.route) {
                                inclusive = true
                            }
                        }
                        NotificationService(context).showNotification(
                            title = tripPackageName,
                            content = "Booking confirmed! Have a great stay."
                        )
                    },
                    onViewOrderButtonClick = {
                        showDialog = it
                        navController.navigate(AppScreen.OrderGraph.route) {
                            popUpTo(AppScreen.Home.route)
                        }
                        NotificationService(context).showNotification(
                            title = tripPackageName,
                            content = "Booking confirmed! Have a great stay."
                        )
                    },
                    onDismissButtonClick = {
                        showDialog = !showDialog
                    }
                )
            } else {
                val context = LocalContext.current
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "Insufficient slots are available", Toast.LENGTH_LONG)
                        .show()
                    showDialog = !showDialog
                }
            }
        }
    }
}

@Composable
fun TripPackageCount(
    label: String,
    description: String,
    quantity: Int,
    onIncrement: (Int) -> Unit,
    onDecrement: (Int) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Person, contentDescription = label)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = label,
                    style = AppTheme.typography.mediumSemiBold
                )
                Text(
                    text = description,
                    style = AppTheme.typography.smallRegular
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onDecrement(quantity - 1) }) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Decrease $label",
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = "$quantity",
                style = AppTheme.typography.mediumBold
            )
            IconButton(
                onClick = { onIncrement(quantity + 1) }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Increase $label",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

fun isValidCardNumber(cardNumber: String, selectedPaymentMethod: PaymentMethod): Boolean {
    return cardNumber.length == 16 && selectedPaymentMethod != PaymentMethod.EWallet
}

fun isValidPhoneNumber(cardNumber: String, selectedPaymentMethod: PaymentMethod): Boolean {
    if (cardNumber.startsWith("011") && cardNumber.length == 11 && selectedPaymentMethod == PaymentMethod.EWallet)
        return true
    else if (cardNumber.startsWith("01") && cardNumber.length == 10 && selectedPaymentMethod == PaymentMethod.EWallet)
        return true
    return false
}
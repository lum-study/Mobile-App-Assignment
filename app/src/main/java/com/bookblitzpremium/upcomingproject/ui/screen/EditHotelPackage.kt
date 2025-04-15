package com.bookblitzpremium.upcomingproject.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceScreen(
) {
    // State for dropdowns
    var customer by remember { mutableStateOf("VYDEO") }
    var invoiceDate by remember { mutableStateOf("04/06/2023") }
    var dueDate by remember { mutableStateOf("ON Receipt") }
    var customerExpanded by remember { mutableStateOf(false) }
    var dateExpanded by remember { mutableStateOf(false) }
    var dueExpanded by remember { mutableStateOf(false) }

    // Sample customer and date options (replace with real data)
    val customerOptions = listOf("VYDEO", "Customer A", "Customer B")
    val dateOptions = listOf("04/06/2023", "05/06/2023", "06/06/2023")
    val dueOptions = listOf("ON Receipt", "Due in 7 Days", "Due in 30 Days")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Light gray background
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.batik_air_bg), // Add this image
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp), // Offset content to overlap the image slightly
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Greeting and Share Icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hello, ALANOVE",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                IconButton(onClick = { /* TODO: Share action */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.logo), // Add this icon
                        contentDescription = "Share",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            // Card for Dropdowns
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Customer Dropdown
                    ExposedDropdownMenuBox(
                        expanded = customerExpanded,
                        onExpandedChange = { customerExpanded = !customerExpanded }
                    ) {
                        TextField(
                            value = customer,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Customer") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = customerExpanded)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                unfocusedIndicatorColor = Color.Gray
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = customerExpanded,
                            onDismissRequest = { customerExpanded = false }
                        ) {
                            customerOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        customer = option
                                        customerExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Invoice Date and Due Date Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Invoice Date Dropdown
                        ExposedDropdownMenuBox(
                            expanded = dateExpanded,
                            onExpandedChange = { dateExpanded = !dateExpanded },
                            modifier = Modifier.weight(1f)
                        ) {
                            TextField(
                                value = invoiceDate,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Invoice Date") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dateExpanded)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                    unfocusedIndicatorColor = Color.Gray
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = dateExpanded,
                                onDismissRequest = { dateExpanded = false }
                            ) {
                                dateOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            invoiceDate = option
                                            dateExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        // Due Date Dropdown
                        ExposedDropdownMenuBox(
                            expanded = dueExpanded,
                            onExpandedChange = { dueExpanded = !dueExpanded },
                            modifier = Modifier.weight(1f)
                        ) {
                            TextField(
                                value = dueDate,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Due") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dueExpanded)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                    unfocusedIndicatorColor = Color.Gray
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = dueExpanded,
                                onDismissRequest = { dueExpanded = false }
                            ) {
                                dueOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            dueDate = option
                                            dueExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Continue Button
            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Continue",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
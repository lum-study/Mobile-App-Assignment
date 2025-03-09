package com.BookBlitzPremium.Upcomingproject.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.BookBlitzPremium.Upcomingproject.R


@Preview(showBackground = true)
@Composable
fun HomeMain() {
    val username = "Your Name"
    AppTheme{
        Column(
            modifier = Modifier.fillMaxSize().statusBarsPadding()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 32.dp)
            ) {
                Column (
                    Modifier.weight(1f).padding(start = 20.dp)
                ){
                    Text(
                        text = stringResource(id = R.string.home_greeting, username),
                        style = AppTheme.typography.displayLarge,
                    )
                    Text(
                        text = stringResource(id = R.string.home_greeting, username)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.green_mountain),
                    contentDescription = "Mountain landscape background",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            }

            SearchBar(query = "Search", { newText -> newText })
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
){
    Row {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search...") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            trailingIcon = {
                Icon(imageVector = Icons.Filled.Tune, contentDescription = "Filter Icon")
            },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,  // Hide underline when focused
                unfocusedIndicatorColor = Color.Transparent,  // Hide underline when not focused
                disabledIndicatorColor = Color.Transparent  // Hide underline when disabled
            ),
        )
    }
}
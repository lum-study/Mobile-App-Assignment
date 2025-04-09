package com.bookblitzpremium.upcomingproject.ui.components

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.bookblitzpremium.upcomingproject.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

@Composable
fun Base64Image(
    base64String: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val imageBitmap = remember(base64String) { decodeBase64ToImageBitmap(base64String) }

    imageBitmap?.let {
        Image(
            bitmap = it,
            contentDescription = stringResource(R.string.base64_image),
            modifier = modifier,
            contentScale = contentScale,
        )
    }
}

@Composable
fun UrlImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    var base64Image by remember { mutableStateOf("") }

    LaunchedEffect(imageUrl) {
        if (imageUrl.isNotEmpty()) {
            base64Image = getBase64FromUrl(imageUrl)
        }
    }

    Base64Image(
        base64String = base64Image,
        modifier = modifier,
        contentScale = contentScale
    )
}

private fun decodeBase64ToImageBitmap(base64String: String): ImageBitmap? {
    return try {
        val pureBase64 = base64String.substringAfter("base64,")
        val decodedBytes = Base64.decode(pureBase64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)?.asImageBitmap()
    } catch (e: Exception) {
        null
    }
}

private suspend fun getBase64FromUrl(url: String): String = withContext(Dispatchers.IO) {
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    try {
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful || response.body == null) return@withContext ""

            response.body!!.byteStream().use { inputStream ->
                Base64.encodeToString(inputStream.readBytes(), Base64.NO_WRAP)
            }
        }
    } catch (e: Exception) {
        ""
    }
}
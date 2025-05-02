package com.bookblitzpremium.upcomingproject.ui.utility

import android.content.Context
import android.widget.Toast

object ToastUtils {
    private var toast: Toast? = null

    fun showSingleToast(context: Context, message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}
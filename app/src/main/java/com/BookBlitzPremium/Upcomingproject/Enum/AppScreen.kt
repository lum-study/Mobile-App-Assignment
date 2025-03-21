package com.BookBlitzPremium.Upcomingproject.Enum

import androidx.annotation.StringRes
import com.BookBlitzPremium.Upcomingproject.R

enum class AppScreen(@StringRes val title: Int) {
    SearchNavigation(title = R.string.search_navigation),
    Search(title = R.string.search_title),
    Result(title = R.string.filtered_result),
    Filter(title = R.string.filter_title),
}

package com.bookblitzpremium.upcomingproject.common.enums

sealed class AppScreen(val route: String, val hasTopBar: Boolean, val hasBottomBar: Boolean) {
    data object Default : AppScreen("", hasTopBar = false, hasBottomBar = false)

    // Auth Screens
    data object AuthGraph : AppScreen("AuthGraph", hasTopBar = false, hasBottomBar = true)
    data object Login : AppScreen("Login", hasTopBar = false, hasBottomBar = false)
    data object Register : AppScreen("Register", hasTopBar = false, hasBottomBar = false)
    data object OTP : AppScreen("OTP", hasTopBar = false, hasBottomBar = false)
    data object ForgotPassword : AppScreen("ForgotPassword", hasTopBar = false, hasBottomBar = false)
    data object VerifyEmailWaiting : AppScreen("VerifyEmailWaiting", hasTopBar = false, hasBottomBar = false)
    data object ChangePassword : AppScreen("ChangePassword", hasTopBar = false, hasBottomBar = false)
    data object GenderScreen: AppScreen("GenderScreen", hasTopBar = false, hasBottomBar = false )
    data object WelcomeLoginScreen: AppScreen("WelcomeLoginScreen", hasTopBar = false , hasBottomBar = false)
    data object WelcomeRegristerScreen : AppScreen("WelcomeRegristerScreen", hasTopBar = false , hasBottomBar = false)

    // Home Screens
    data object HomeGraph : AppScreen("HomeGraph", hasTopBar = false, hasBottomBar = false)
    data object Home : AppScreen("Home", hasTopBar = false, hasBottomBar = true)

    data object TripPackage : AppScreen("TripPackage", hasTopBar = true, hasBottomBar = false)
    data object Schedule : AppScreen("Schedule", hasTopBar = true, hasBottomBar = false)
    data object Flight : AppScreen("Flight", hasTopBar = true, hasBottomBar = false)
    data object Hotel : AppScreen("Hotel", hasTopBar = false, hasBottomBar = false)
    data object TripPackageBooking : AppScreen("TripPackageBooking", hasTopBar = true, hasBottomBar = false)

    // Search Screens
    data object SearchGraph : AppScreen("SearchGraph", hasTopBar = false, hasBottomBar = false)
    data object Search : AppScreen("Search", hasTopBar = false, hasBottomBar = true)
    data object Result : AppScreen("Result", hasTopBar = true, hasBottomBar = true)
    data object Filter : AppScreen("Filter", hasTopBar = true, hasBottomBar = false)

    // Order
    data object OrderGraph : AppScreen("OrderGraph", hasTopBar = false, hasBottomBar = true)
    data object MyOrders : AppScreen("MyOrders", hasTopBar = false, hasBottomBar = true)
    data object Ratings : AppScreen("Ratings", hasTopBar = true, hasBottomBar = false)

    // Profile Screens
    data object ProfileGraph : AppScreen("ProfileGraph", hasTopBar = false, hasBottomBar = false)
    data object Profile : AppScreen("Profile", hasTopBar = false, hasBottomBar = true)
    data object EditProfile : AppScreen("EditProfile", hasTopBar = true, hasBottomBar = false)
    data object PaymentMethods : AppScreen("PaymentMethods", hasTopBar = true, hasBottomBar = false)
    data object RatingRecords : AppScreen("RatingRecords", hasTopBar = true, hasBottomBar = false)
    data object PaymentHotels: AppScreen("PaymentHotels", hasTopBar = false, hasBottomBar = false )

    // Booking Date and People
    data object BookingDate : AppScreen("BookingDate", hasTopBar = true, hasBottomBar = false)
    data object BookingPeople : AppScreen("BookingPeople", hasTopBar = true, hasBottomBar = false)
    data object BookingReview : AppScreen("BookingReview", hasTopBar = true, hasBottomBar = false)
    data object BookingHistory : AppScreen("BookingHistory", hasTopBar = true, hasBottomBar = false)
    data object EditScreen : AppScreen("EditScreen", hasTopBar = true, hasBottomBar = false)
    data object Maps: AppScreen("Maps", hasTopBar = false, hasBottomBar = false)

    // Entry Page
    data object EntryPage : AppScreen("EntryPage", hasTopBar = false, hasBottomBar = false)
    data object EntryPage2 : AppScreen("EntryPage2", hasTopBar = false, hasBottomBar = false)

    fun passData(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    companion object {
        fun fromRoute(route: String?): AppScreen {
            val baseRoute = route?.substringBefore('/') ?: AuthGraph.route

            return when (baseRoute) {
                AuthGraph.route -> AuthGraph
                Login.route -> Login
                Register.route -> Register
                OTP.route -> OTP
                GenderScreen.route -> GenderScreen
                ForgotPassword.route -> ForgotPassword
                VerifyEmailWaiting.route -> VerifyEmailWaiting
                ChangePassword.route -> ChangePassword
                WelcomeLoginScreen.route -> WelcomeLoginScreen
                WelcomeRegristerScreen.route -> WelcomeRegristerScreen
                HomeGraph.route -> HomeGraph
                Home.route -> Home
                TripPackage.route -> TripPackage
                Schedule.route -> Schedule
                Flight.route -> Flight
                Hotel.route -> Hotel
                Maps.route -> Maps
                TripPackageBooking.route -> TripPackageBooking
                SearchGraph.route -> SearchGraph
                Search.route -> Search
                Result.route -> Result
                Filter.route -> Filter
                OrderGraph.route -> OrderGraph
                MyOrders.route -> MyOrders
                Ratings.route -> Ratings
                ProfileGraph.route -> ProfileGraph
                Profile.route -> Profile
                EditProfile.route -> EditProfile
                PaymentMethods.route -> PaymentMethods
                RatingRecords.route -> RatingRecords
                PaymentHotels.route -> PaymentHotels
                BookingDate.route -> BookingDate
                BookingPeople.route -> BookingPeople
                BookingReview.route -> BookingReview
                BookingHistory.route -> BookingHistory
                EditScreen.route -> EditScreen
                EntryPage.route -> EntryPage
                EntryPage2.route -> EntryPage2
                else -> Default
            }
        }
    }
}

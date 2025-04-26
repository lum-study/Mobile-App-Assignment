plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.bookblitzpremium.upcomingproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bookblitzpremium.upcomingproject"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.12.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth") // ✅ added
    implementation("com.google.android.gms:play-services-auth:21.3.0")
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore.ktx)

    implementation(libs.coil.compose)
    implementation(libs.zxing.android.embedded)

    implementation("androidx.compose.material3:material3:1.3.0")
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation(libs.androidx.core.ktx)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx")

    implementation("androidx.compose.material3:material3-window-size-class:1.0.1")
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.scenecore)
    implementation(libs.androidx.adaptive.android)

    implementation("androidx.media3:media3-exoplayer:1.2.0")
    implementation("androidx.media3:media3-ui:1.2.0")

    //notication
    implementation("com.google.accompanist:accompanist-permissions:0.30.1")
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.play.services.auth)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //room
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    testImplementation("androidx.room:room-testing:2.6.1")
    androidTestImplementation("androidx.room:room-testing:2.6.1")
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.paging.compose)
    implementation(libs.androidx.room.paging)

    //hilt
    implementation(libs.hilt.android)
    implementation (libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.compiler)
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.compiler)

    //compare password
    implementation("org.mindrot:jbcrypt:0.4")
}
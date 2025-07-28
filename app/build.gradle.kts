plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
//For FCM
//    id ("com.android.application")
    id("com.google.gms.google-services")
    alias(libs.plugins.google.firebase.crashlytics)

}

android {
    namespace = "com.itecknologi.itecktestingcompose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.itecknologi.itecktestingcompose"
        minSdk = 26
        targetSdk = 35
        versionCode = 3
        versionName = "1.2"

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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.storage)
    implementation(libs.androidx.navigation.compose)
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.firebase.crashlytics)
    implementation(libs.androidx.work.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(libs.androidx.lifecycle.runtime.ktx.v287)
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("androidx.activity:activity-compose:1.10.0")
    implementation("androidx.camera:camera-camera2:1.3.0-alpha01")
    implementation("androidx.camera:camera-lifecycle:1.3.0-alpha01")
    implementation("androidx.camera:camera-view:1.3.0-alpha01")

    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")

    implementation("com.google.android.gms:play-services-location:21.3.0")

//For FCM
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.12.0"))
    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics")


    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:4.9.3")




    implementation("com.airbnb.android:lottie-compose:6.3.0") // latest stable version as of 2025
    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation (libs.firebase.config) //For Firebase remote config

    implementation ("com.google.accompanist:accompanist-swiperefresh:0.35.1-alpha")


    implementation("androidx.compose.material3:material3:1.3.2")
    implementation("androidx.compose.material3:material3-window-size-class:1.3.2")
    implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.4.0-alpha18")






}
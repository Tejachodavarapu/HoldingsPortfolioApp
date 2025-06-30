plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.compose.compiler)
    kotlin("kapt")

}

android {
    namespace = "com.teja.holdingsdemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.teja.holdingsdemo"
        minSdk = 25
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    hilt {
        enableAggregatingTask = false
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
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.adaptive.android)
    implementation(libs.androidx.benchmark.common)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.material3.window.size.class1.android)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.work.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit for all API calls
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Security for the EncryptedSharedPreferences
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.security.crypto)

    // Jetpack Compose Navigation (for navigating after login)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.material3)
    implementation(libs.accompanist.adaptive)

    //Constraint layout
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.material3)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.hilt.android)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    //RoomDB
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.coil.compose)
    implementation (libs.security.crypto.v110alpha06)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.androidx.browser)
    implementation(libs.accompanist.flowlayout)
    implementation (libs.accompanist.placeholder.material)
    implementation(libs.timber)




}
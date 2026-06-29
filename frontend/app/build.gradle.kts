plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.amanakk"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.amanakk"
        minSdk = 26
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
    buildToolsVersion = "34.0.0"
}
android {
    signingConfigs {
        create("release") {
            storeFile = file("C:/Users/ROG/amank/amankk.jks") // Replace with your Keystore path
            storePassword = "NewPassword123" // Replace with your Keystore password
            keyAlias = "amanak_key" // Replace with your alias
            keyPassword = "NewPassword123" // Replace with your key password
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true // Optional: Enable ProGuard for release builds
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
}



dependencies {
    implementation (libs.bcpkix.jdk15to18) // Latest version for cryptographic support
    implementation (libs.bcprov.jdk15to18)  // Core BouncyCastle library
    implementation (libs.conscrypt.android)  // Conscrypt for SSL/TLS support
    implementation (libs.navigation.fragment.ktx.v251)
    implementation (libs.navigation.ui.ktx.v251)
    implementation(libs.material.v180)
    implementation(libs.drawerlayout)
    implementation(libs.material.v161)
    implementation(libs.appcompat.v131)
    implementation(libs.activity.v123)
    implementation(libs.constraintlayout.v210)
    implementation(libs.smart.reply.common)

    implementation(libs.recyclerview.v121)
    implementation(libs.glide)
    implementation(libs.datastore.core.android)
    implementation(libs.room.common)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    annotationProcessor(libs.compiler)
    implementation(libs.picasso) // Added Picasso for image loading
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.v113)
    androidTestImplementation(libs.espresso.core.v340)


}

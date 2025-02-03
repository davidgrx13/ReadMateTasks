import org.gradle.kotlin.dsl.android
import org.gradle.kotlin.dsl.libs

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "1.9.10"


}

android {
    namespace = "com.example.readmatetasks"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.readmatetasks"
        minSdk = 25
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
    composeOptions {
        kotlinCompilerExtensionVersion = "2.0.0" // Alineado con Kotlin 2.0
    }

}

dependencies {


    //camara??
    implementation (libs.coil.compose.v210)

    implementation(libs.coil.compose)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation (libs.androidx.core.ktx.v1120)


    //Firerstone

    implementation (platform(libs.firebase.bom.v3200))
    implementation (libs.google.firebase.firestore.ktx)
    implementation (libs.com.google.firebase.firebase.firestore.ktx)
    implementation (libs.kotlinx.coroutines.play.services)

    //barras del sistema
    implementation (libs.accompanist.systemuicontroller)
    implementation(libs.androidx.foundation.layout)

    //Compose
    // Componentes principales de Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.navigation.compose.v273)
    implementation(libs.lottie.compose)


    // Navegación en Compose
    implementation(libs.androidx.navigation.compose)
    implementation (libs.androidx.lifecycle.runtime.compose)
    implementation(libs.firebase.storage)
    implementation(libs.androidx.compose.material.core)
    implementation(libs.androidx.media3.exoplayer)


    // Debugging
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation (libs.coil.compose.v222)



    // Testing de Compose
    androidTestImplementation(libs.androidx.ui.test.junit4)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
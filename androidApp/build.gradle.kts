plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    id("com.google.devtools.ksp") version "1.9.23-1.0.20"
    kotlin("plugin.serialization") version "1.9.10"
}

android {
    namespace = "me.mrsajal.flashcart.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "me.mrsajal.flashcart.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.material)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.koin.androidx.compose)

    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.coil.compose)
}
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt") // Необходимо для KAPT
}

android {
    namespace = "com.example.kotlin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kotlin"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Основные библиотеки Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Replace with the latest version
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Gson converter for JSON parsing
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    // Зависимость Glide
    implementation("com.github.bumptech.glide:glide:4.14.2")
    implementation(libs.androidx.preference.ktx) // Используйте последнюю версию
    kapt("com.github.bumptech.glide:compiler:4.14.2") // KAPT для Glide

    // Тестирование
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}


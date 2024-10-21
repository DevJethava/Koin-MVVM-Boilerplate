plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp.android)
}

android {
    namespace = "com.devjethava.koinboilerplate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.devjethava.koinboilerplate"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    buildTypes {
        release {
            buildConfigField("String", "API_URL", "\"https://randomuser.me/api/\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }

        debug {
            buildConfigField("String", "API_URL", "\"https://randomuser.me/api/\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        viewBinding = true
        //noinspection DataBindingWithoutKapt
        dataBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Multidex
    implementation(libs.multidex)

    // Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Retrofit
    implementation(libs.retrofitLib)
    implementation(libs.converter.gson)
    implementation(libs.adapter.rxjava2)
    implementation(libs.googleGSON)

    // OkHttp3
    implementation(libs.okhttpLib)
    implementation(libs.logging.interceptor)

    // ViewMode and Lifecycle Extension
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
//    ksp(libs.lifecycle.compiler)

    // Glide Image Loader
    implementation(libs.glide)

    // Koin DI
    implementation(libs.koin.android)
    // Koin for Tests
    testImplementation(libs.koin.test.junit4)

    // Reactive
    implementation(libs.rxandroid)
    implementation(libs.rxjava)
    implementation(libs.autodispose)
    implementation(libs.autodispose.android.archcomponents)
    implementation(libs.rxkotlin)

    // Architecture Component - Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
}
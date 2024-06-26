@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.tutorial.searchsuggestions"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tutorial.searchsuggestions"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "API_ENDPOINT", "\"https://duckduckgo.com\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Android
    implementation(libs.core.ktx)
    implementation(libs.browser)
    implementation(libs.lifecycle.runtime.ktx)

    // Network
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.material.icons.extended)

    // Compose Support
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    // Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Test
    testImplementation(libs.junit)
    testImplementation(libs.androidx.arch.core.testing)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.rules)
    testImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)

    // Android Test
    androidTestImplementation(libs.androidx.arch.core.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(libs.mockito.kotlin)

    // Debug Tools
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}

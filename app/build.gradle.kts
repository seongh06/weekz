import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.lucas.weekz"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.lucas.weekz"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val properties = Properties()
        val propertiesFile = project.rootProject.file("local.properties")

        if (propertiesFile.exists()) {
            properties.load(propertiesFile.inputStream())
        } else {
            throw GradleException("local.properties file not found")
        }

        val apiKey = properties.getProperty("API_KEY") as String?

        if (apiKey.isNullOrEmpty()) {
            throw GradleException("API key not found in local.properties. Please add API_KEY=YOUR_API_KEY.")
        } else {
            buildConfigField("String", "apiKey", "\"$apiKey\"")
        }
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
// Configure Kotlin JVM toolchain here
kotlin {
    jvmToolchain(17) // Configure the JVM toolchain within the kotlin block
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
    implementation(libs.generativeai)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.navigation:navigation-compose:2.5.3")

    // preview
    // implementation("androidx.compose.ui:ui-tooling-preview:1.2.0")
    // debugImplementation("androidx.compose.ui:ui-tooling:1.2.0")

    // hilt
    implementation("com.google.dagger:hilt-android:2.50")
    ksp("com.google.dagger:hilt-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation("androidx.compose.runtime:runtime-android:1.8.0") // Use your current Compose version

    implementation("com.google.accompanist:accompanist-placeholder:0.34.0") // 최신 버전 확인하여 사용
    implementation("com.google.accompanist:accompanist-placeholder-material:0.34.0") // Material Design 스타일 Shimmer 사용 시 추가
    implementation("com.google.accompanist:accompanist-placeholder-material3:0.34.0") // Material3 스타일 Shimmer 사용 시 추가
}
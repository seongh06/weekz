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


    val properties = Properties()
    val propertiesFile = project.rootProject.file("local.properties")

    if (propertiesFile.exists()) {
        properties.load(propertiesFile.inputStream())
    } else {
        throw GradleException("local.properties file not found")
    }

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
        val apiKey = properties.getProperty("API_KEY") as String?

        if (apiKey.isNullOrEmpty()) {
            throw GradleException("API key not found in local.properties. Please add API_KEY=YOUR_API_KEY.")
        } else {
            buildConfigField("String", "apiKey", "\"$apiKey\"")
        }
    }
    signingConfigs {
        create("release") {
            // local.properties에서 로드한 'properties' 객체에서 서명 속성 읽어오기
            val storeFileProp = properties.getProperty("STORE_FILE") as String?
            val storePasswordProp = properties.getProperty("STORE_PASSWORD") as String?
            val keyAliasProp = properties.getProperty("KEY_ALIAS") as String?
            val keyPasswordProp = properties.getProperty("KEY_PASSWORD") as String?

            if (storeFileProp.isNullOrEmpty() || storePasswordProp.isNullOrEmpty() || keyAliasProp.isNullOrEmpty() || keyPasswordProp.isNullOrEmpty()) {
                // 속성이 local.properties에 있어야 함을 알리는 오류 메시지로 업데이트
                throw GradleException("Signing properties not found or empty in local.properties. Please check STORE_FILE, STORE_PASSWORD, KEY_ALIAS, KEY_PASSWORD.")
            }

            storeFile = file(storeFileProp)
            storePassword = storePasswordProp
            keyAlias = keyAliasProp
            keyPassword = keyPasswordProp
        }
    }
    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true // 코드 축소 활성화
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release") // 릴리스 서명 구성 적용 (필요시)
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
    implementation(libs.androidx.core.splashscreen)
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
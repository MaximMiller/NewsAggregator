import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android) apply true
    alias(libs.plugins.ksp)
}

fun getNewsApiKey(): String {
    val properties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (!localPropertiesFile.exists()) {
        throw GradleException("Файл local.properties не найден! Добавьте NEWS_API_KEY.")
    }
    localPropertiesFile.inputStream().use { properties.load(it) }
    return properties.getProperty("NEWS_API_KEY")
        ?: throw GradleException("NEWS_API_KEY не задан в local.properties!")
}

android {
    namespace = "com.example.newsaggregator"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.newsaggregator"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "NEWS_API_KEY", "\"${getNewsApiKey()}\"")
        buildConfigField("String", "NEWS_API_BASE_URL", "\"https://newsapi.org/v2/\"")
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3" // Совместимо с Kotlin 1.9.0
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

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

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.ui)
    implementation(libs.material3)
    implementation(libs.ui.tooling.preview)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Retrofit
    implementation(libs.retrofit2.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    // Coil
    implementation(libs.coil.compose)

    // Other
    implementation(libs.androidx.core.ktx.v1120)
    implementation(libs.androidx.lifecycle.runtime.ktx.v262)
}
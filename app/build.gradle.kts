import java.util.Properties
import java.io.FileInputStream
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Jika Anda menggunakan Room dengan room-compiler, Anda perlu plugin Kapt di sini
    alias(libs.plugins.kotlin.kapt) // <--- UNCOMMENT BARIS INI
}

android {
    namespace = "com.example.aripa"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.aripa"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Jika Anda ingin menambahkan API_KEY atau field kustom lainnya ke BuildConfig
        // Anda juga perlu baris ini dan file local.properties yang sesuai
         val localProperties = Properties()
         val localPropertiesFile = rootProject.file("local.properties")
         if (localPropertiesFile.exists()) {
             localProperties.load(FileInputStream(localPropertiesFile))
         }
        buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("GEMINI_API_KEY")}\"")
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
        viewBinding = true
        buildConfig = true // <--- TAMBAHKAN BARIS INI UNTUK MENGGENERATE BuildConfig
    }

}

dependencies {
    // =========================================================
    // DEPENDENSI YANG SUDAH DIKONSOLIDASI & DIKOREKSI ALIASNYA
    // =========================================================

    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // UI & Material Design
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    // Lifecycle Components (aliases corrected based on your libs.versions.toml)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // =========================================================
    // MODIFIKASI INI: DEKLARASI LANGSUNG UNTUK NAVIGATION
    // Ini adalah WORKAROUND untuk "Unresolved reference: navigation"
    // Versi diambil dari libs.versions.toml (navigationFragmentKtx = "2.9.0", navigationUiKtx = "2.9.0")
    // =========================================================
    implementation("androidx.navigation:navigation-fragment-ktx:2.9.0") // Mengatasi libs.androidx.navigation.fragment.ktx
    implementation("androidx.navigation:navigation-ui-ktx:2.9.0")       // Mengatasi libs.androidx.navigation.ui.ktx


    // Activity KTX
    implementation(libs.androidx.activity)

    // Networking & JSON Parsing (OkHttp & Gson)
    implementation(libs.okhttp)
    implementation(libs.gson)

    // Markwon (for Markdown rendering)
    implementation(libs.core)

    // Room (aliases should be correct as per your libs.versions.toml)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler) // <--- UNCOMMENT BARIS INI
    implementation(libs.room.ktx) // <--- UNCOMMENT BARIS INI

    // Testing Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
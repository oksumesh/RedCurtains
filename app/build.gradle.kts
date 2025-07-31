plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
    
    // Add Firebase App Distribution plugin
    id("com.google.firebase.appdistribution")
}

android {
    namespace = "com.example.redcurtains"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.redcurtains"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.2"

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
}

dependencies {
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:34.0.0"))

    // Note: Firebase App Distribution is a Gradle plugin, not a runtime dependency
    
    // Add Firebase Analytics for app insights
    implementation("com.google.firebase:firebase-analytics")
    
    // Add Firebase Crashlytics for crash reporting
    implementation("com.google.firebase:firebase-crashlytics")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Firebase App Distribution configuration
firebaseAppDistribution {
    artifactType = "APK"
    releaseNotesFile = "release_notes.txt"
    groups = "testers"
}
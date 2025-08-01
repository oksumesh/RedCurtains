// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.3" apply false
    
    // Add Firebase Crashlytics plugin
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    
    // Add Firebase App Distribution plugin
    id("com.google.firebase.appdistribution") version "4.2.0" apply false
}
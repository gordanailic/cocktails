buildscript {

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.49")

    }
    repositories {
        google()
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0-Beta1" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false

}


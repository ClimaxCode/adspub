// Top-level build file where you can add configuration options common to all sub-projects/modules.


buildscript {
    dependencies {

        classpath ("com.applovin.quality:AppLovinQualityServiceGradlePlugin:+")
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
}
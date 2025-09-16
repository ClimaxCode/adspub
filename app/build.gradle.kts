plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "com.github.ClimaxCode"
version = "1.0.0"

android {
    namespace = "com.climax.code"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.climax.testcodeqs"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    viewBinding {
        enable = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11" // ✅ REQUIRED for Kotlin 1.9.24
    }
    buildFeatures{
        compose = true
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.05.00") // ✅ Stable BOM
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.core.ktx.v190)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.process)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.play.services.ads)
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)
    implementation(libs.shimmer)
    implementation("com.tbuonomo:dotsindicator:5.1.0")
    implementation(project(":adspub"))
    implementation(project(":customizations"))
    implementation ("com.airbnb.android:lottie:6.6.7")

  //  implementation ("com.applovin:applovin-sdk:13.3.0")
  //  implementation ("com.applovin.mediation:facebook-adapter:6.18.0.1")
}

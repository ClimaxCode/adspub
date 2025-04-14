plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

group = "com.github.ClimaxCode"
version = "1.0.0"

android {
    namespace = "com.climax.code"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.climax.testcode"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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

    viewBinding {
        enable = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
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
    implementation("com.tbuonomo:dotsindicator:5.0")
    implementation(project(":adspub"))
    implementation(project(":customizations"))
    implementation ("com.airbnb.android:lottie:3.4.0")

    implementation ("com.applovin:applovin-sdk:13.0.1")
    implementation ("com.applovin.mediation:facebook-adapter:6.18.0.1")
}

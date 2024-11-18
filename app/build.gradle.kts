plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
    signing
}

group = "com.github.ClimaxCode"
version = "1.0.6"

android {
    namespace = "com.example.ads"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ads"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.play.services.ads)
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)
    implementation(libs.shimmer)

    implementation("com.github.ClimaxCode:ads:1.0.6") // Replace with your actual dependency
}
configure<PublishingExtension>{
    publications {
        create<MavenPublication>("mavenJava") {
            // Publish the Android library artifact


            // Configure POM metadata (optional)
            pom {
                name.set("Ads Library")
                description.set("A simple ads library for Android applications.")
                url.set("https://github.com/ClimaxCode/adspub")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

//                developers {
//                    developer {
//                        id.set("ClimaxCode")
//                        name.set("Climax Code")
//                        email.set("youremail@example.com")
//                    }
//                }

                scm {
                    connection.set("scm:git:git://github.com/ClimaxCode/adspub.git")
                    developerConnection.set("scm:git:ssh://github.com/ClimaxCode/adspub.git")
                    url.set("https://github.com/ClimaxCode/adspub")
                }
            }
        }
    }

    repositories {
        maven {
            url = uri("https://jitpack.io") // Publish to JitPack
        }
    }
}

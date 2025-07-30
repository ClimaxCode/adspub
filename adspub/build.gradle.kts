plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
    signing
}

android {
    namespace = "com.climax.ads"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    viewBinding {
        enable = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx.v190)
   // implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.play.services.ads)
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)
    implementation(libs.shimmer)
    implementation ("com.applovin:applovin-sdk:13.0.1")
    implementation ("com.applovin.mediation:facebook-adapter:6.18.0.1")
}

afterEvaluate {
    configure<PublishingExtension>{
        publications {
            create<MavenPublication>("mavenJava") {
                // Publish the Android library artifact
                from(components["release"])


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

}
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
    signing
}

android {
    namespace = "com.climax.code"
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
    kotlinOptions {
        jvmTarget = "17"
    }
    viewBinding {
        enable = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx.v190)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":adspub"))

    implementation(libs.sdp.android)
    implementation(libs.ssp.android)
    implementation(libs.shimmer)
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
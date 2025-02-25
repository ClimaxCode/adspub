pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        mavenLocal()
     //   maven { url = uri("https://jitpack.io") }
        gradlePluginPortal()
        maven { url = uri("https://artifacts.applovin.com/android") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        mavenCentral()
        maven { url = uri("https://artifacts.applovin.com/android") }
    }
}

rootProject.name = "Ads"
include(":app")
include(":adspub")
include(":customizations")

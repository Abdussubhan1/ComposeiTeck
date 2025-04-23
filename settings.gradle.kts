pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        kotlin("jvm") version "1.9.22" // Replace with your kotlin version
        id("com.google.devtools.ksp") version "2.0.21-1.0.27" // Replace with the latest KSP version
        id("com.android.application") version "8.3.1"
        id("org.jetbrains.kotlin.android") version "1.9.22"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "itecktestingcompose"
include(":app")
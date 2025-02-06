import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    //Network client
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    kotlin("plugin.serialization") version "2.1.0"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.coil.compose)
            implementation("com.google.accompanist:accompanist-systemuicontroller:0.36.0")
            implementation("io.ktor:ktor-client-android:3.0.3")

            implementation("androidx.security:security-crypto:1.1.0-alpha06")
            implementation ("androidx.datastore:datastore-preferences:1.1.2")
            implementation("com.russhwolf:multiplatform-settings:1.3.0")
            implementation(libs.ktor.client.okhttp)
            implementation(libs.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.coil.compose)
            implementation(libs.ktor.client.darwin)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(projects.shared)

            implementation(libs.coil.compose)
            implementation(libs.coil.svg)
            implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.6.0")
            implementation("com.squareup.okio:okio:3.10.2")
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")
            implementation("io.coil-kt.coil3:coil-network-ktor3:3.0.4")

            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)

            implementation(libs.client.ktorfit)

            //key-value secure storage
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0") // JSON Serialization
//            implementation("com.soywiz.korlibs.krypto:krypto:4.0.10") // AES Encryption
            implementation("androidx.datastore:datastore-preferences-core:1.1.2")

            val ktor_version = "3.0.2"
            // https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-util
            implementation(libs.ktor.client.core)
//            implementation(libs.ktor.client.okhttp)
//            implementation(libs.ktor.client.cio)

//            implementation("io.ktor:ktor-client-core:$ktor_version")
//            implementation("io.ktor:ktor-client-apache5:$ktor_version")
//            implementation("io.ktor:ktor-client-java:$ktor_version")
//            implementation("io.ktor:ktor-client-jetty:$ktor_version")
//            implementation("org.eclipse.jetty:jetty-alpn-java-client:11.0.20")
//            implementation("io.ktor:ktor-client-cio:$ktor_version")
//            implementation("io.ktor:ktor-client-android:$ktor_version")
//            implementation("io.ktor:ktor-client-okhttp:$ktor_version")



        }
    }
}

android {
    namespace = "com.rahullohra.instagram"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.rahullohra.instagram"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}


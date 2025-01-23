plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
    kotlin("plugin.serialization") version "2.1.0"
}

group = "com.rahullohra.instagram"
version = "1.0.0"
application {
    mainClass.set("com.rahullohra.instagram.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
//    testImplementation(libs.ktor.server.tests) //Need to comment it for issue: Could not find io.ktor:ktor-junit:3.0.2.
    testImplementation(libs.kotlin.test.junit)

    implementation("org.jetbrains.exposed:exposed-core:0.58.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.58.0")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:0.58.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.58.0")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.0")
    implementation("com.h2database:h2:2.2.224")
    implementation("io.ktor:ktor-server-content-negotiation:3.0.2") // https://ktor.io/docs/client-serialization.html
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.2")

}
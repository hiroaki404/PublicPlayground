import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    compileOnly(gradleApi())
    implementation(libs.bundles.plugins)
}

gradlePlugin {
    plugins {
        register("PublicPlaygroundApplicationPlugin") {
            id = "publicplayground.android.application"
            implementationClass = "PublicPlaygroundApplicationPlugin"
        }
    }
}

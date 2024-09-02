plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
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
    plugins {
        register("PublicPlaygroundLibraryPlugin") {
            id = "publicplayground.android.library"
            implementationClass = "PublicPlaygroundLibraryPlugin"
        }
    }
    plugins {
        register("KotlinAndroidPlugin") {
            id = "publicplayground.android.kotlin"
            implementationClass = "AndroidKotlinPlugin"
        }
    }
    plugins {
        register("PublicPlaygroundAndroidPlugin") {
            id = "publicplayground.android.compose"
            implementationClass = "AndroidComposePlugin"
        }
    }
}

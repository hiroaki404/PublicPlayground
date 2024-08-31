plugins {
    id("publicplayground.android.library")
    id("publicplayground.android.kotlin")
}

android {
    namespace = "com.example.kotest"

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {
    testImplementation(libs.kotest.runner)
    testImplementation(libs.kotest.assertions.core)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

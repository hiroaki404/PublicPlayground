plugins {
    alias(libs.plugins.publicplayground.android.library)
    alias(libs.plugins.publicplayground.android.kotlin)
}

android.namespace = "com.example.turbine"

dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.turbin)
    testImplementation(libs.androidx.test.truth)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.kotest.assertions.core)
}

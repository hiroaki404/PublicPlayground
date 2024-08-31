plugins {
    id("publicplayground.android.library")
    id("publicplayground.android.kotlin")
}

android.namespace = "com.example.turbine"

dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.turbin)
    testImplementation(libs.androidx.test.truth)
    testImplementation(libs.coroutines.test)
}

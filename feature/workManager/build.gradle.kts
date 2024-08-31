plugins {
    id("publicplayground.android.library")
    id("publicplayground.android.kotlin")
}

android {
    namespace = "com.example.publicplayground.feature.workmanager"
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

plugins {
    alias(libs.plugins.publicplayground.android.library)
    alias(libs.plugins.publicplayground.android.kotlin)
}

android.namespace = "com.example.publicplayground.feature.workmanager"

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

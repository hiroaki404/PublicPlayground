plugins {
    alias(libs.plugins.publicplayground.android.library)
    alias(libs.plugins.publicplayground.android.kotlin)
    alias(libs.plugins.ksp)
}

android.namespace = "com.example.publicplayground.feature.workmanager"

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.workManager)
    implementation(libs.androidx.hilt.worker)
    ksp(libs.androidx.hilt.compiler)

    testImplementation(libs.workManager.test)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.test.junit.ktx)
    testImplementation(libs.coroutines.test)
}

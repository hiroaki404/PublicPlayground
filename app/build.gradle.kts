plugins {
    alias(libs.plugins.publicplayground.android.application)
    alias(libs.plugins.publicplayground.android.compose)
    alias(libs.plugins.publicplayground.android.kotlin)
    alias(libs.plugins.kotlin.serialization)
}

android.namespace = "com.example.publicplayground"

dependencies {
    implementation(projects.feature.animation)
    implementation(projects.feature.composeBasic)
    implementation(projects.feature.workManager)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.animation)
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // workManager
    implementation(libs.workManager)

    implementation(libs.coil3)
    implementation(libs.okio)

    // maven github package or local
//    implementation("sample:shared-android:1.0")

    implementation(libs.androidx.navigation.compose)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)
}

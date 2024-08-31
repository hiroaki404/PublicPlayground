plugins {
    id("publicplayground.android.library")
    id("publicplayground.android.compose")
    id("publicplayground.android.kotlin")
}

android.namespace = "com.example.feature.animation"

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    testImplementation(libs.junit)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
}

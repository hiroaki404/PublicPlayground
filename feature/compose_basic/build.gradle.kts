plugins {
    id("publicplayground.android.library")
    id("publicplayground.android.kotlin")
    id("publicplayground.android.compose")
}

android.namespace = "com.example.publicplayground.feature.compose_basic"

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

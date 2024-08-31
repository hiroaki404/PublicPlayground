
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

fun Project.android(action: BaseAppModuleExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.androidCommon(action: com.android.build.gradle.TestedExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.setupAndroid() {
    android {
        val libs: VersionCatalog =
            extensions.getByType<VersionCatalogsExtension>().named("libs")

        compileSdk = libs.findVersion("compileSdk").get().toString().toInt()

        defaultConfig {
            applicationId = "com.example.publicplayground"
            minSdk = libs.findVersion("minSdk").get().toString().toInt()
            targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
            versionCode = 1
            versionName = "1.0"

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        buildFeatures {
            compose = true
        }
    }
}


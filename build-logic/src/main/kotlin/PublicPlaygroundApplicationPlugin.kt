import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class PublicPlaygroundApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            androidApplication {
                setupAndroid()

                defaultConfig {
                    applicationId = "com.example.publicplayground"
                    targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
                    versionCode = 1
                    versionName = "1.0"

                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                buildFeatures {
                    buildConfig = true
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
            }
        }
    }
}

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class PublicPlaygroundApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            androidApplication {
                setupAndroid()
                val libs: VersionCatalog =
                    extensions.getByType<VersionCatalogsExtension>().named("libs")


                defaultConfig {
                    applicationId = "com.example.publicplayground"
                    targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
                    versionCode = 1
                    versionName = "1.0"

                    vectorDrawables {
                        useSupportLibrary = true
                    }
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

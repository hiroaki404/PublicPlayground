import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidComposePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            android {
                buildFeatures {
                    compose = true
                }
            }
        }
    }
}


import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class PublicPlaygroundLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

        }
    }
}

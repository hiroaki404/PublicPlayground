pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // use maven local repository
//        mavenLocal()
        // use maven github package repository
//        maven {
//            url = uri("https://maven.pkg.github.com/hiroaki404/kmp-local-library-sample")
//            credentials {
//                val localProperties = Properties()
//                localProperties.load(FileInputStream(file("local.properties")))
//                username = "${localProperties["gpr.user"]}"
//                password = "${localProperties["gpr.key"]}"
//            }
//        }
    }
}

rootProject.name = "PublicPlayground"
include(":app")
include(":feature:animation")
include(":test:turbine")
include(":feature:compose_basic")
include(":test:kotest")
include(":feature:workManager")
include(":cameraXSample")

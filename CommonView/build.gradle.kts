plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
//    alias(libs.plugins.android.library)
//    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

afterEvaluate {
    publishing { // 发布配置
        publications { // 发布的内容
            create<MavenPublication>("release") {
                groupId = "com.github.Rany-k"
                artifactId = "CommonStyleViews-CommonView"
                version = "1.0.0-Beta"
                from(components.getByName("release"))
            }
        }
    }
}

android {
    namespace = "com.ranycess.commonview"
    compileSdk = 34

    defaultConfig {
        minSdk = 19
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    implementation(project(":ViewStyleCore"))
}
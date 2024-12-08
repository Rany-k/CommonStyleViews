plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

afterEvaluate {
    publishing { // 发布配置
        publications { // 发布的内容
            create<MavenPublication>("release") {
                groupId = "com.github.Rany-k"
                artifactId = "CommonStyleViews"
                version = "0.1.0-SNAPSHOT"
            }
        }
    }
}

android {
    namespace = "com.ranycees.ranyview.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }


}

dependencies {
    implementation(libs.androidx.core.ktx)
}
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
                artifactId = "CommonStyleViews-Core"
                version = "0.1.0-SNAPSHOT"
                from(components.getByName("release"))
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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
}
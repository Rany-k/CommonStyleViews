import org.gradle.internal.declarativedsl.parsing.main
import org.gradle.kotlin.dsl.resolver.buildSrcSourceRootsFilePath

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    implementation(project(":ViewStyleCore"))
//    implementation(project(":CommonView"))
//    implementation(files("libs/ViewStyleCore.aar"))
//    implementation(files("libs/CommonView.aar"))
    implementation("com.github.Rany-k.CommonStyleViews:CommonStyleViews-CommonView:1.0.0-Beta02")
    implementation("com.github.Rany-k.CommonStyleViews:CommonStyleViews-Core:1.0.0-Beta02")
}
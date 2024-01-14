plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.myapplication001"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.myapplication001"
        minSdk = 29
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation ("com.zhy:okhttputils:2.6.2")
    implementation ("com.google.code.gson:gson:2.8.6")
    implementation ("com.squareup.picasso:picasso:2.5.2")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.0.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.0.pr3")
    implementation("com.fasterxml.jackson.core:jackson-core:2.9.0.pr3")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.9.0.pr3")
}
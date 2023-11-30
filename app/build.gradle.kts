plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.scciapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.scciapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("io.getstream:avatarview-coil:1.0.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")



    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))

    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.owlike:genson:1.6")

    // build.gradle

// build.gradle
    implementation("com.google.modernstorage:modernstorage-filesystem:1.0.0-alpha03")
// https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple
//    implementation("com.googlecode.json-simple:json-simple:1.1.1")



    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.2")


}

//configurations.all {
//    resolutionStrategy.dependencySubstitution {
//        substitute module('org.hamcrest:hamcrest-core:1.1') with module('junit:junit:4.10')
//    }
//}


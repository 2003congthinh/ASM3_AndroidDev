plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "myapk.asm3"
    compileSdk = 34

    defaultConfig {
        applicationId = "myapk.asm3"
        minSdk = 26
        targetSdk = 34
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

    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-analytics")


    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-analytics-ktx:21.2.0")
    implementation("com.google.firebase:firebase-database:20.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.google.android.gms:play-services-auth-api-phone:18.0.1")
    implementation("androidx.recyclerview:recyclerview:1.2.1");


    implementation("com.google.android.material:material:1.0.0") // For design library
    implementation("com.firebaseui:firebase-ui:0.6.0")



    // Gmail authentication
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation ("com.google.android.gms:play-services-identity:18.0.1")

    // Swipe card
    implementation("com.lorentzos.swipecards:library:1.0.9")
    implementation("com.github.bumptech.glide:glide:4.12.0") // Use the latest version from the official Glide GitHub repository
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    // Current location
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.maps.android:android-maps-utils:2.2.1")

    implementation("io.socket:socket.io-client:2.1.0") {
        // excluding org.json which is provided by Android
        exclude(group = "org.json", module = "json")
    }


}
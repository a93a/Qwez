plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.qwez"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] =
                        "$projectDir/schemas"
            }
        }
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    namespace = "com.example.qwez"

    lintOptions {
        isAbortOnError = false
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("com.google.firebase:firebase-crashlytics:18.2.10")
    implementation("com.google.firebase:firebase-analytics:21.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test:rules:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")

    testImplementation("org.powermock:powermock-core:2.0.2")
    testImplementation("org.powermock:powermock-api-mockito2:2.0.2")
    testImplementation("org.powermock:powermock-module-junit4-rule-agent:2.0.2")
    testImplementation("org.powermock:powermock-module-junit4-rule:2.0.2")
    testImplementation("org.powermock:powermock-module-junit4:2.0.2")

    // Overriding conflicted dependencies
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.media:media:1.6.0")

    // Dagger2
    implementation("com.google.dagger:dagger:2.42")
    kapt("com.google.dagger:dagger-compiler:2.42")
    implementation("com.google.dagger:dagger-android:2.42")
    implementation("com.google.dagger:dagger-android-support:2.42")
    kapt("com.google.dagger:dagger-android-processor:2.42")

    // Firebase
    implementation("com.google.firebase:firebase-auth:21.0.4")
    implementation("com.google.firebase:firebase-core:21.0.0")
    implementation("com.google.firebase:firebase-firestore:24.1.2")
    implementation("com.google.firebase:firebase-storage:20.0.1")
    implementation("com.google.firebase:firebase-functions:20.1.0")
    implementation("com.google.firebase:firebase-messaging:23.0.4")
    testImplementation("com.google.firebase:firebase-auth:21.0.4")
    testImplementation("com.google.firebase:firebase-core:21.0.0")
    testImplementation("com.google.firebase:firebase-firestore:24.1.2")
    testImplementation("com.google.firebase:firebase-storage:20.0.1")
    testImplementation("com.google.firebase:firebase-functions:20.1.0")
    testImplementation("com.google.firebase:firebase-messaging:23.0.4")

    implementation("com.google.firebase:firebase-iid:21.1.0")
    // Firebase Crashlytics / Fabric

    // RxJava
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")
    implementation("io.reactivex.rxjava2:rxjava:2.2.9")
    // implementation("com.jakewharton.rxbinding3:rxbinding:3.0.0-alpha2")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.5.0")

    // OkHttp
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.8")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.8")

    // Mockito
    testImplementation("org.mockito:mockito-core:2.25.0")

    // Android Components
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    kapt("androidx.lifecycle:lifecycle-common-java8:2.4.1")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.4.1")
    implementation("androidx.lifecycle:lifecycle-reactivestreams:2.4.1")
    implementation("androidx.preference:preference:1.2.0")

    // Android Components Test Helpers
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("androidx.room:room-testing:2.4.2")

    // Room
    // DONT UPDATE TO 2.1.0-alpha06 !!! IT HAS A BUG WHICH IS A PAIN IN THE ASS !!!
    // implementation("androidx.room:room-runtime:2.1.0-alpha04")
    // kapt("androidx.room:room-compiler:2.1.0-alpha04")
    // implementation("androidx.room:room-rxjava2:2.1.0-alpha04")
    // androidTestImplementation("androidx.room:room-testing:2.1.0-alpha04")
    implementation("androidx.room:room-runtime:2.5.0-alpha01")
    kapt("androidx.room:room-compiler:2.5.0-alpha01")
    implementation("androidx.room:room-rxjava2:2.5.0-alpha01")
    androidTestImplementation("androidx.room:room-testing:2.5.0-alpha01")
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    // ButterKnife
    implementation("com.jakewharton:butterknife:10.1.0")
    kapt("com.jakewharton:butterknife-compiler:10.1.0")

    // LeakCanary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.9.1")
    releaseImplementation("com.squareup.leakcanary:leakcanary-android-no-op:2.9.1")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // Material Design
    implementation("com.google.android.material:material:1.6.0")

    // Lottie
    implementation("com.airbnb.android:lottie:3.0.0")

    // Design
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.6.0")

    // MaterialDialogs USE OLDER VERSION THAT IS COMPATIBLE WITH JAVA. NEWER IS ONLY KOTLIN COMPATIBLE
    //noinspection GradleDependency
    implementation("com.afollestad.material-dialogs:core:0.9.6.0")

    // Timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    // CardView
    implementation("androidx.cardview:cardview:1.0.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.9.0")
    kapt("com.github.bumptech.glide:compiler:4.9.0")

    // Stetho
    implementation("com.facebook.stetho:stetho:1.5.1")

    // Apache Commons Language
    implementation("org.apache.commons:commons-text:1.6")

    // RxPermissions
    implementation("com.github.tbruyelle:rxpermissions:0.10.2")
}

kapt {
    correctErrorTypes = true
}
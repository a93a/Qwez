plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.example.qwez"
        minSdk = 24
        targetSdk = 32
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
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Modules
    implementation(project(":firebasewrapper"))

    // AndroidX
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.extensions)
    kapt(libs.lifecycle.common.java8)
    implementation(libs.lifecycle.common.java8)
    implementation(libs.lifecycle.reactivestreams)
    implementation(libs.preference)
    androidTestImplementation(libs.core.testing)
    androidTestImplementation(libs.room.testing)
    implementation(libs.legacy.support.v4)
    implementation(libs.media)

    // AndroidX Test
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.test.rules)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.test.ext.junit)

    // JUnit
    testImplementation(libs.junit)

    // Powermock
    testImplementation(libs.powermock.core)
    testImplementation(libs.powermock.api.mockito2)
    testImplementation(libs.powermock.module.junit4.rule.agent)
    testImplementation(libs.powermock.module.junit4.rule)
    testImplementation(libs.powermock.module.junit4)

    // Dagger2
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    kapt(libs.dagger.android.processor)

    // Firebase
    implementation(libs.firebase.auth)
    implementation(libs.firebase.core)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.functions)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.iid)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    testImplementation(libs.firebase.auth)
    testImplementation(libs.firebase.core)
    testImplementation(libs.firebase.firestore)
    testImplementation(libs.firebase.storage)
    testImplementation(libs.firebase.functions)
    testImplementation(libs.firebase.messaging)

    // RxJava
    implementation(libs.rxandroid)
    implementation(libs.rxjava)
    // Optional: Uncomment if needed
    // implementation(libs.rxbinding)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.adapter.rxjava2)

    // OkHttp
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.okhttp)

    // Mockito
    testImplementation(libs.mockito.core)

    // Room
    // DONT UPDATE TO 2.1.0-alpha06 !!! IT HAS A BUG WHICH IS A PAIN IN THE ASS !!!
    // implementation("androidx.room:room-runtime:2.1.0-alpha04")
    // kapt("androidx.room:room-compiler:2.1.0-alpha04")
    // implementation("androidx.room:room-rxjava2:2.1.0-alpha04")
    // androidTestImplementation("androidx.room:room-testing:2.1.0-alpha04")
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.rxjava2)
    androidTestImplementation(libs.room.testing)
    testImplementation(libs.core.testing)

    // ButterKnife
    implementation(libs.butterknife)
    kapt(libs.butterknife.compiler)

    // LeakCanary
    debugImplementation(libs.leakcanary.debug)
    releaseImplementation(libs.leakcanary.release)

    // Material Design
    implementation(libs.material)

    // Lottie
    implementation(libs.lottie)

    // MaterialDialogs USE OLDER VERSION THAT IS COMPATIBLE WITH JAVA. NEWER IS ONLY KOTLIN COMPATIBLE
    //noinspection GradleDependency
    // MaterialDialogs (use older version for Java compatibility)
    implementation(libs.material.dialogs)

    // Timber
    implementation(libs.timber)

    // CardView
    implementation(libs.cardview)

    // Glide
    implementation(libs.glide)
    kapt(libs.glide.compiler)

    // Stetho
    implementation(libs.stetho)

    // Apache Commons Language
    implementation(libs.commons.text)

    // RxPermissions
    implementation(libs.rxpermissions)
}

kapt {
    correctErrorTypes = true
}
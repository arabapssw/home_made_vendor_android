plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
}



android {
    compileSdkVersion(AndroidSdk.compile)
    buildToolsVersion(AndroidSdk.buildToolVersion)

    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)

        testInstrumentationRunner ("androidx.test.runner.AndroidJUnitRunner")
        consumerProguardFiles ("consumer-rules.pro")
    }

    buildFeatures {
        viewBinding = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(Libraries.ktxCore)
    implementation(project(Libraries.utilsModule))
    implementation(project(Libraries.loginModule))
    implementation(project(Libraries.homeModule))
    implementation(Libraries.appCompat)
    implementation(Libraries.googleMaterial)
    implementation (Libraries.constraintLayout)
    testImplementation (TestLibraries.junit4)
    implementation (Libraries.corotineCore)
    implementation(Libraries.DotsIndicatorLibrary)




}
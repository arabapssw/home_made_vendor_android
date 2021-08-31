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
//    implementation(project(Libraries.categoriesModule))
    implementation(project(Libraries.coreModule))
//    implementation(project(Libraries.searchModule))
//    implementation(project(Libraries.filterModule))

    implementation(Libraries.paging3)


    implementation(Libraries.appCompat)
    implementation(Libraries.googleMaterial)
    implementation (Libraries.constraintLayout)
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    testImplementation (TestLibraries.junit4)
    implementation (Libraries.corotineCore)
    implementation(Libraries.DotsIndicatorLibrary)

}
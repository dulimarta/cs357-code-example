import org.jetbrains.kotlin.cfg.pseudocode.and

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.apollographql.apollo") version "4.3.2"
}

android {
    namespace = "edu.gvsu.cis357.spacex_graphql"
    compileSdk = 36

    defaultConfig {
        applicationId = "edu.gvsu.cis357.spacex_graphql"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/graphql")
        }
    }
}

apollo {
    service("SpaceX") {
        // Convert abc.def.xyz into abc/def/xyz
        generateKotlinModels.set(true)
        packageNamesFromFilePaths(android.namespace)
        schemaFile.set(file("src/main/graphql/edu/gvsu/cis357/spacex_graphql/schema.graphqls"))
        srcDir(file("src/main/graphql/edu/gvsu/cis357/spacex_graphql/"))
//        srcDir(file("src/main/graphql/${pkgDir}/"))
        introspection {
            endpointUrl.set("https://spacex-production.up.railway.app/")
        }
    }
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("com.apollographql.apollo:apollo-runtime:4.3.2")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
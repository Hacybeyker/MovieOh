plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("jacoco")
    id("org.sonarqube") version "5.1.0.4882"
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
}

apply {
    from("sonarqube.gradle")
    from("jacoco.gradle")
}

android {
    namespace = ConfigureApp.applicationId
    compileSdk = AppVersion.compileSdkVersion

    defaultConfig {
        applicationId = ConfigureApp.applicationId
        minSdk = AppVersion.minSdkVersion
        targetSdk = AppVersion.targetSdkVersion
        versionCode = ConfigureApp.versionCode
        versionName = ConfigureApp.versionName
        testInstrumentationRunner = AppVersion.testInstrumentationRunner
        renderscriptSupportModeEnabled = true
        vectorDrawables.useSupportLibrary = true
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    testOptions {
        animationsDisabled = true
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = findProperty("SIGNING_KEY_ALIAS_MOVIEOH") as String?
                ?: System.getenv("SIGNING_KEY_ALIAS")
            keyPassword = findProperty("SIGNING_KEY_PASSWORD_HACYBEYKER") as String?
                ?: System.getenv("SIGNING_KEY_PASSWORD")
            storeFile = file("../.signing/release-movieoh-key.jks")
            storePassword = findProperty("SIGNING_STORE_PASSWORD_HACYBEYKER") as String?
                ?: System.getenv("SIGNING_STORE_PASSWORD")
        }
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "BASE_URL", ConstantsApp.Release.BASE_URL)
            buildConfigField(
                "String",
                "BASE_URL_PLATFORMS",
                ConstantsApp.Release.BASE_URL_PLATFORMS,
            )
            buildConfigField(
                "boolean",
                "IS_DEVELOPMENT",
                ConstantsApp.Release.IS_DEVELOPMENT.toString(),
            )
        }
        create("qa") {
            initWith(getByName("debug"))
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "BASE_URL", ConstantsApp.QA.BASE_URL)
            buildConfigField(
                "String",
                "BASE_URL_PLATFORMS",
                ConstantsApp.Release.BASE_URL_PLATFORMS,
            )
            buildConfigField(
                "boolean",
                "IS_DEVELOPMENT",
                ConstantsApp.QA.IS_DEVELOPMENT.toString(),
            )
        }
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            // signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "BASE_URL", ConstantsApp.Debug.BASE_URL)
            buildConfigField(
                "String",
                "BASE_URL_PLATFORMS",
                ConstantsApp.Release.BASE_URL_PLATFORMS,
            )
            buildConfigField(
                "boolean",
                "IS_DEVELOPMENT",
                ConstantsApp.Debug.IS_DEVELOPMENT.toString(),
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }

    bundle {
        language {
            enableSplit = true
        }
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
    }

    lint {
        disable.addAll(
            listOf(
                "TypographyFractions",
                "TypographyQuotes",
                "JvmStaticProvidesInObjectDetector",
                "FieldSiteTargetOnQualifierAnnotation",
                "ModuleCompanionObjects",
                "ModuleCompanionObjectsNotInModuleParent",
            ),
        )
        checkDependencies = true
        abortOnError = false
        ignoreWarnings = false
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        jvmTarget = JavaVersion.VERSION_17.toString()
        exclude("**/build/**")
        exclude("**/resources/**")
        exclude("**/tmp/**")
        exclude("**/generated/**")
        exclude("**/test/**")
        exclude("**/androidTest/**")

        reports {
            html.required.set(true)
            xml.required.set(true)
            txt.required.set(false)
            sarif.required.set(false)
            md.required.set(false)
        }
    }

    detekt {
        buildUponDefaultConfig = true
        allRules = true
        autoCorrect = true
        config.setFrom("$projectDir/config/detekt.yml")
        baseline = file("$projectDir/config/baseline.xml")
        parallel = true
        ignoreFailures = false
        basePath = project.rootProject.projectDir.absolutePath
    }

    ktlint {
        android.set(true)
        ignoreFailures.set(false)
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        }
    }

    tasks {
        "preBuild" {
            dependsOn("ktlintFormat")
            dependsOn("ktlintCheck")
            dependsOn("detekt")
        }
    }
}

dependencies {
    // Core
    implementation(fileTree("libs") { include(listOf("*.jar", "*.aar")) })
    implementation(AppDependencies.coreKtx)
    // View
    implementation(AppDependencies.appCompat)
    implementation(AppDependencies.material)
    implementation(AppDependencies.constraintLayout)
    implementation(AppDependencies.viewPager2)
    implementation(AppDependencies.lottie)
    // Hilt
    implementation(AppDependencies.hilt)
    kapt(AppDependencies.hiltCompiler)
    // ViewModel & Livedata
    implementation(AppDependencies.lifecycleViewModel)
    implementation(AppDependencies.lifecycleLiveData)
    implementation(AppDependencies.lifecycleRuntime)
    // Coroutines
    implementation(AppDependencies.coroutinesCore)
    implementation(AppDependencies.coroutinesAndroid)
    // Retrofit
    implementation(AppDependencies.retrofit)
    implementation(AppDependencies.converterGson)
    implementation(AppDependencies.loggingInterceptor)
    implementation(AppDependencies.okHttpJsonMock)
    // Room
    implementation(AppDependencies.roomRuntime)
    kapt(AppDependencies.roomCompiler)
    implementation(AppDependencies.roomKtx)
    // Glide
    implementation(AppDependencies.glide)
    kapt(AppDependencies.glideCompiler)
    // Shimmer Facebook
    implementation(AppDependencies.shimmerFacebook)
    // Test
    testImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.extJUnit)
    androidTestImplementation(TestDependencies.espressoCore)
    testImplementation(TestDependencies.robolectric)
    testImplementation(TestDependencies.archCore)
    testImplementation(TestDependencies.coreKtx)
    testImplementation(TestDependencies.junitKtx)
    testImplementation(TestDependencies.kotlinCoroutines)
    testImplementation(TestDependencies.mockitoKotlin)
    testImplementation(TestDependencies.mockitoInline)
    // Chucker
    debugImplementation(AppDependencies.chucker)
    "qaImplementation"(AppDependencies.chucker)
    releaseImplementation(AppDependencies.chuckerNoOp)
    // Detekt
    detektPlugins(ValidationDependencies.detekt)
    // Library
    api(project(":uikit"))
}

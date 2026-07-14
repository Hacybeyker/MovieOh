import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.sonarqube)
    jacoco
}

object AppConfig {
    const val ORGANIZATION = "hacybeyker"
    const val PROJECT_NAME = "app-movieoh-android"
    const val APPLICATION_ID = "com.hacybeyker.movieoh"

    object Release {
        const val BASE_URL = "\"https://api.themoviedb.org/3/\""
        const val BASE_URL_PLATFORMS = "\"https://www.buscala.tv/api/\""
        const val IS_DEVELOPMENT = false
    }

    object Debug {
        const val BASE_URL = "\"https://hacybeyker/mocks/\""
        const val BASE_URL_PLATFORMS = "\"https://www.buscala.tv/api/\""
        const val IS_DEVELOPMENT = true
    }

    object QA {
        const val BASE_URL = "\"https://api.themoviedb.org/3/\""
        const val BASE_URL_PLATFORMS = "\"https://www.buscala.tv/api/\""
        const val IS_DEVELOPMENT = false
    }
}

val localProperties =
    Properties().apply {
        val propertiesFile = rootProject.file("local.properties")
        if (propertiesFile.exists()) {
            propertiesFile.inputStream().use { load(it) }
        }
    }

fun secret(name: String): String = localProperties.getProperty(name) ?: System.getenv(name) ?: ""

android {
    namespace = AppConfig.APPLICATION_ID
    compileSdk =
        libs.versions.compile.sdk
            .get()
            .toInt()

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdk =
            libs.versions.min.sdk
                .get()
                .toInt()
        targetSdk =
            libs.versions.target.sdk
                .get()
                .toInt()
        versionCode =
            libs.versions.app.version.code
                .get()
                .toInt()
        versionName =
            libs.versions.app.version.name
                .get()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        buildConfigField("String", "KEY_PROD", "\"${secret("KEY_PROD")}\"")
        buildConfigField("String", "KEY_QA", "\"${secret("KEY_QA")}\"")
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
            buildConfigField("String", "BASE_URL", AppConfig.Release.BASE_URL)
            buildConfigField("String", "BASE_URL_PLATFORMS", AppConfig.Release.BASE_URL_PLATFORMS)
            buildConfigField("boolean", "IS_DEVELOPMENT", AppConfig.Release.IS_DEVELOPMENT.toString())
        }
        create("qa") {
            initWith(getByName("debug"))
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "BASE_URL", AppConfig.QA.BASE_URL)
            buildConfigField("String", "BASE_URL_PLATFORMS", AppConfig.QA.BASE_URL_PLATFORMS)
            buildConfigField("boolean", "IS_DEVELOPMENT", AppConfig.QA.IS_DEVELOPMENT.toString())
        }
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "BASE_URL", AppConfig.Debug.BASE_URL)
            buildConfigField("String", "BASE_URL_PLATFORMS", AppConfig.Debug.BASE_URL_PLATFORMS)
            buildConfigField("boolean", "IS_DEVELOPMENT", AppConfig.Debug.IS_DEVELOPMENT.toString())
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
            ),
        )
        checkDependencies = true
        abortOnError = false
        ignoreWarnings = false
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    autoCorrect = false
    config.setFrom("$projectDir/config/detekt.yml")
    parallel = true
    ignoreFailures = false
    basePath = project.rootProject.projectDir.absolutePath
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = JavaVersion.VERSION_21.toString()
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

ktlint {
    android.set(true)
    ignoreFailures.set(false)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}

// ---------------------------------------------------------------------------
// Coverage (JaCoCo)
// ---------------------------------------------------------------------------

jacoco {
    toolVersion = libs.versions.jacoco.get()
}

val coverageExclusions =
    listOf(
        "**/R.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        "**/*Module*.*",
        "**/*Dagger*.*",
        "**/*Hilt*.*",
        "**/*MembersInjector*.*",
        "**/*_Factory*.*",
        "**/*_Provide*Factory*.*",
        "**/hilt_aggregated_deps/**",
        "**/codegen/**",
        "**/commons/**",
        "**/data/api/**",
        "**/data/model/**",
        "**/di/**",
        "**/domain/entity/**",
        "**/utils/**",
        // Compose UI (covered by UI tests, not unit tests)
        "**/MainActivity*",
        "**/ComposableSingletons*",
        "**/ui/components/**",
        "**/ui/navigation/**",
        "**/ui/theme/**",
        "**/ui/**/*Screen*",
        "**/ui/**/*UiState*",
    )

val jacocoTestReport =
    tasks.register<JacocoReport>("jacocoTestReport") {
        dependsOn("testDebugUnitTest")
        group = "jacocoReport"

        reports {
            xml.required.set(true)
            html.required.set(true)
        }

        val debugTree =
            fileTree("${layout.buildDirectory.get().asFile}/tmp/kotlin-classes/debug") {
                exclude(coverageExclusions)
            }
        sourceDirectories.setFrom(files("$projectDir/src/main/java"))
        classDirectories.setFrom(files(debugTree))
        executionData.setFrom(files("${layout.buildDirectory.get().asFile}/jacoco/testDebugUnitTest.exec"))
    }

tasks.register<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    dependsOn(jacocoTestReport)
    group = "jacocoReport"

    val debugTree =
        fileTree("${layout.buildDirectory.get().asFile}/tmp/kotlin-classes/debug") {
            exclude(coverageExclusions)
        }
    sourceDirectories.setFrom(files("$projectDir/src/main/java"))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(files("${layout.buildDirectory.get().asFile}/jacoco/testDebugUnitTest.exec"))

    violationRules {
        isFailOnViolation = true
        rule {
            limit {
                minimum = "0.0".toBigDecimal()
            }
        }
    }
}

tasks.withType<Test>().configureEach {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
    testLogging {
        events("passed", "skipped", "failed")
    }
}

// ---------------------------------------------------------------------------
// SonarQube
// ---------------------------------------------------------------------------

sonar {
    properties {
        property("sonar.projectKey", AppConfig.APPLICATION_ID)
        property("sonar.organization", AppConfig.ORGANIZATION)
        property("sonar.projectName", AppConfig.PROJECT_NAME)
        property("sonar.projectDescription", "This project is ${AppConfig.PROJECT_NAME}")
        property(
            "sonar.projectVersion",
            libs.versions.app.version.name
                .get(),
        )
        property("sonar.projectBaseDir", projectDir.absolutePath)
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.coverage.jacoco.xmlReportPaths", "${layout.buildDirectory.get().asFile}/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
        property("sonar.coverage.exclusions", coverageExclusions.joinToString(","))
        property("sonar.androidLint.reportPaths", "${layout.buildDirectory.get().asFile}/reports/lint-results-debug.xml")
        property("sonar.junit.reportPaths", "${layout.buildDirectory.get().asFile}/test-results/testDebugUnitTest")
        property("sonar.kotlin.detekt.reportPaths", "${layout.buildDirectory.get().asFile}/reports/detekt/detekt.xml")
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons.core)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    // Storage
    implementation(libs.androidx.datastore.preferences)
    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.okhttp.json.mock)
    // Images
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    // Chucker
    debugImplementation(libs.chucker)
    "qaImplementation"(libs.chucker)
    releaseImplementation(libs.chucker.no.op)
    // Test
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.test.core.ktx)
    testImplementation(libs.androidx.test.ext.junit.ktx)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Detekt
    detektPlugins(libs.detekt.formatting)
    // Library
    api(project(":uikit"))
}

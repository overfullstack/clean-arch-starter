import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") apply false
    id("com.adarshr.test-logger") version "2.1.0" 
    id("io.gitlab.arturbosch.detekt") version "1.11.0"
}

subprojects {

    group = "io.overfullstack"
    version = "0.0.1-SNAPSHOT"

    repositories {
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://dl.bintray.com/kotlin/kotlin-dev")
    }

    apply {
        plugin("kotlin")
        plugin("com.adarshr.test-logger")
        plugin("io.gitlab.arturbosch.detekt")
    }

    dependencies {
        val implementation by configurations
        val testImplementation by configurations
        val runtimeOnly by configurations
        
        // Logging
        implementation("io.github.microutils:kotlin-logging:+")
        runtimeOnly("org.apache.logging.log4j:log4j-slf4j18-impl:+")
        
        // Kotest
        testImplementation("io.kotest:kotest-runner-junit5:+")
        testImplementation("io.kotest:kotest-assertions-core:+")
    }

    // These belong to subprojects
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_14.toString()
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform {
            excludeEngines("junit-vintage")
        }
        jvmArgs("--enable-preview")
    }

    testlogger {
        setTheme("mocha")
        showExceptions=true
        showStackTraces=true
        showFullStackTraces=false
        showCauses=true
        slowThreshold=2000
        showSummary=true
        showSimpleNames=true
        showPassed=true
        showSkipped=true
        showFailed=true
        showStandardStreams=true
        showPassedStandardStreams=true
        showSkippedStandardStreams=true
        showFailedStandardStreams=true
    }

    detekt {
        baseline = file("${rootProject.projectDir}/config/baseline.xml")
        config = files("config/detekt/detekt.yml")
        buildUponDefaultConfig = true
    }
}



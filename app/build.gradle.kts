plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.jda)
    implementation(libs.logback)
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter("5.10.0")
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("org.hyperskill.community.hyper.App")
}

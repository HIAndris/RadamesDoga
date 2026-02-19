import java.io.ByteArrayOutputStream

// Project main details
group = "hiandris.radames"
val projectName = "RadamesDoga"
version = getGitVersion()

// Get the current version from git without the "v" prefix
fun getGitVersion(): String {
    return try {
        val stdout = ByteArrayOutputStream()
        providers.exec {
            commandLine("git", "describe", "--tags", "--abbrev=0")
            standardOutput = stdout
            isIgnoreExitValue = true
        }
        val gitTag = stdout.toString().trim()

        gitTag.removePrefix("v")
    } catch (e: Exception) {
        "0.0.1"
    }
}

plugins {
    java
    application
    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "2.25.0"
}

repositories {
    mavenCentral()
}

val junitVersion = "5.12.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("hiandris.radames")
    mainClass.set("hiandris.radames.Launcher")
}

javafx {
    version = "21.0.6"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("org.controlsfx:controlsfx:11.2.1")
    implementation("net.synedra:validatorfx:0.6.1") {
        exclude(group = "org.openjfx")
    }
    implementation("org.kordamp.ikonli:ikonli-javafx:12.3.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "zip-6", "--no-header-files", "--no-man-pages"))
    launcher {
        name = projectName
    }

    jpackage {
        vendor = "HIAndris"
        appVersion = "${project.version}"

        if (org.gradle.internal.os.OperatingSystem.current().isWindows) {
            // WINDOWS
            installerOptions.addAll(listOf(
                "--win-dir-chooser",
                "--win-menu",
                "--win-menu-group", projectName,
                "--win-shortcut",
                "--win-per-user-install"
            ))

        } else if (org.gradle.internal.os.OperatingSystem.current().isLinux) {
            // LINUX
            installerOptions.addAll(listOf(
                "--linux-shortcut",
                "--linux-menu-group", projectName
            ))
        }
    }
}
import org.gradle.kotlin.dsl.jpackage
import kotlin.collections.addAll

plugins {
    java
    application
    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "2.25.0"
}

group = "hiandris.radames"
version = "0.1.0"
val projectName = "RadamesDoga"

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
    mainClass.set("hiandris.radames.HelloApplication")
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
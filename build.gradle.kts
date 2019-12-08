import org.jetbrains.intellij.tasks.PublishTask

val pluginGroup: String by project
val pluginName: String by project
val pluginVersion: String by project
val pluginDescription: String by project

val ideaVersion: String by project

val publishToken: String by project
val publishChannels: String by project

plugins {
    id("org.jetbrains.intellij") version "0.4.14"
    java
    kotlin("jvm") version "1.3.60"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes(
        """
            <h2>v1.0.0</h2>
            <ul>
                <li>Initial release</li>
            </ul>
            <h4>v1.0.1</h4>
            <ul>
                <li>Update to work with Android Studio</li>
            </ul>
       """
    )
}

group = pluginGroup
description = pluginDescription
version = pluginVersion

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    pluginName = pluginName
    type = "IU"
    version = ideaVersion
    updateSinceUntilBuild = false
}

val publishPlugin: PublishTask by tasks

publishPlugin {
    token(publishToken)
    channels(publishChannels)
}

inline operator fun <T : Task> T.invoke(a: T.() -> Unit): T = apply(a)

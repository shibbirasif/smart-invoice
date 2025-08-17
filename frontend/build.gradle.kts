plugins {
    id("com.github.node-gradle.node") version "7.1.0"
}

node {
    version.set("22.17.0")
    npmVersion.set("10.9.2")
    download.set(true)
    workDir.set(file("${project.projectDir}/.gradle/nodejs"))
}

tasks.register<com.github.gradle.node.npm.task.NpmTask>("buildFrontend") {
    args.set(listOf("run", "build"))
}

tasks.register<com.github.gradle.node.npm.task.NpmTask>("startFrontend") {
    args.set(listOf("run", "start"))
}
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val invoker by configurations.creating

val functions_framework_api_version: String by project
val java_function_invoker_version: String by project
val javafaker_version: String by project
val kotlinx_serialization_json_version: String by project
val kt_rss_reader_version: String by project
val okhttp_version: String by project
val jsoup_version: String by project
val junit_version: String by project

plugins {
    kotlin("jvm") version "1.4.20"
    id("org.jetbrains.kotlin.kapt") version "1.4.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.4.20"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "io.kraftsman"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.google.cloud.functions:functions-framework-api:$functions_framework_api_version")
    invoker("com.google.cloud.functions.invoker:java-function-invoker:$java_function_invoker_version")

    implementation("com.github.javafaker:javafaker:$javafaker_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinx_serialization_json_version")

    implementation("com.github.ivanisidrowu.KtRssReader:kotlin:$kt_rss_reader_version")
    implementation("com.github.ivanisidrowu.KtRssReader:annotation:$kt_rss_reader_version")
    kapt("com.github.ivanisidrowu.KtRssReader:processor:$kt_rss_reader_version")
    implementation("com.squareup.okhttp3:okhttp:$okhttp_version")
    implementation("org.jsoup:jsoup:$jsoup_version")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit_version")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junit_version")
}

kapt {
    arguments {
        arg("pureKotlinParser", true)
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

task<JavaExec>("runFunction") {
    group = "gcp"
    main = "com.google.cloud.functions.invoker.runner.Invoker"
    classpath(invoker, sourceSets.main.get().runtimeClasspath)
    args(
        "--target", project.findProperty("runFunction.target") ?: "",
        "--port", project.findProperty("runFunction.port") ?: 8080
    )
}

tasks.named("build") {
    dependsOn(":shadowJar")
}

task<Copy>("buildFunction") {
    group = "gcp"
    dependsOn("build")
    from("build/libs/${rootProject.name}-${version}-all.jar") {
        rename { "${rootProject.name}.jar" }
    }
    into("build/deploy")
}

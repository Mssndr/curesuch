plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.logging.log4j","log4j-core","2.24.2")
    implementation("org.apache.logging.log4j", "log4j-api", "2.24.2")
    implementation("org.apache.logging.log4j", "log4j-slf4j-impl", "2.24.2")
    implementation("org.apache.commons", "commons-lang3", "3.13.0")
    implementation("org.junit.jupiter", "junit-jupiter-engine", "5.9.2")
    implementation("org.junit.jupiter", "junit-jupiter", "5.9.2")
    implementation("org.junit.jupiter", "junit-jupiter-api", "5.9.2")
    implementation("org.junit.jupiter", "junit-jupiter-params", "5.9.2")
    implementation("org.opentest4j", "opentest4j", "1.2.0")
    implementation("org.junit.platform", "junit-platform-commons", "1.8.2")
    implementation("org.apiguardian", "apiguardian-api", "1.1.2")
    implementation("org.junit.platform", "junit-platform-engine", "1.8.2")
    implementation ("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}



tasks.jar {
    manifest.attributes["Main-Class"] = "org.example.TextQuest"
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree) // OR .map { zipTree(it) }
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.test {
    useJUnitPlatform()
}
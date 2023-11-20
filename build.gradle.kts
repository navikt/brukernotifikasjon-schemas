plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id(AvroPlugin.id) version AvroPlugin.version

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    `maven-publish`
}

repositories {
    mavenCentral()
    mavenLocal()
}

val libraryVersion: String = properties["lib_version"]?.toString() ?: "latest-local"

publishing {
    repositories{
        mavenLocal()
        maven {
            url = uri("https://maven.pkg.github.com/navikt/brukernotifikasjon-schemas")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications {
        create<MavenPublication>("gpr") {
            groupId = "no.nav.tms"
            artifactId = "brukernotifikasjon-schemas"
            version = libraryVersion
            from(components["java"])
        }
    }
}

dependencies {
    implementation(Avro.avro)

    testImplementation(Junit.api)
    testImplementation(Junit.engine)
    testImplementation(Sulky.ulid)
    testImplementation(Hamcrest.hamcrest)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

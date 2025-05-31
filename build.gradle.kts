plugins {
    idea
    `maven-publish`
    id("net.neoforged.gradle.userdev") version "7.0.165"
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"

    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
}

version = project.extra["mod_version"]!!
        group = project.extra["mod_group_id"]!!

        base {
            archivesName.set(project.extra["mod_id"] as String)
        }

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

// minecraft.accessTransformers.file(rootProject.file("src/main/resources/META-INF/accesstransformer.cfg"))
// minecraft.accessTransformers.entry("public net.minecraft.client.Minecraft textureManager # textureManager")

runs {
    configureEach {
        systemProperty("forge.logging.markers", "REGISTRIES")
        systemProperty("forge.logging.console.level", "debug")
        modSource(project.sourceSets["main"])
    }

    create("client") {
        systemProperty("forge.enabledGameTestNamespaces", project.findProperty("mod_id") as String)
    }

    create("server") {
        systemProperty("forge.enabledGameTestNamespaces", project.findProperty("mod_id") as String)
        argument("--nogui")
    }

    create("gameTestServer") {
        systemProperty("forge.enabledGameTestNamespaces", project.findProperty("mod_id") as String)
    }

    create("data") {
        arguments.addAll(
            "--mod", project.findProperty("mod_id") as String,
            "--all", "--output", file("src/generated/resources/").absolutePath,
            "--existing", file("src/main/resources/").absolutePath
        )
    }
}

sourceSets {
    named("main") {
        resources.srcDir("src/generated/resources")
    }
}

repositories {
    mavenLocal()
    maven {
        name = "Kotlin for Forge"
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")

    }
    maven {
        name = "Kore"
        url = uri("https://augustomegener.github.io/Kore/")
    }
}

dependencies {
    implementation("net.neoforged:neoforge:${project.extra["neo_version"]}")
    implementation("thedarkcolour:kotlinforforge-neoforge:5.3.0")

    implementation("augustomegener:Kore:0.1.0c")
    ksp("augustomegener.kore:ksp:0.1.0")
}

tasks.withType<ProcessResources>().configureEach {
    val replaceProperties = mapOf(
            "minecraft_version" to project.extra["minecraft_version"],
            "minecraft_version_range" to project.extra["minecraft_version_range"],
            "neo_version" to project.extra["neo_version"],
            "neo_version_range" to project.extra["neo_version_range"],
            "loader_version_range" to project.extra["loader_version_range"],
            "mod_id" to project.extra["mod_id"],
            "mod_name" to project.extra["mod_name"],
            "mod_license" to project.extra["mod_license"],
            "mod_version" to project.extra["mod_version"],
            "mod_authors" to project.extra["mod_authors"],
            "mod_description" to project.extra["mod_description"],
            "pack_format_number" to project.extra["pack_format_number"]
    )

    inputs.properties(replaceProperties)

    filesMatching(listOf("META-INF/neoforge.mods.toml", "pack.mcmeta")) {
        expand(replaceProperties + mapOf("project" to project))
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("file://${project.projectDir}/repo")
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "21"
}
plugins {
    id("fabric-loom") version "1.5-SNAPSHOT"
    id("signing")
    id("com.diffplug.spotless") version "6+"
}

val minecraftVersion = property("minecraft_version") as String
val modId = property("mod_id") as String

base {
    archivesName = modId
}

version = "${property("mod_version")}+$minecraftVersion"
group = property("maven_group") as String

repositories {
    maven("https://repo.repsy.io/mvn/dicedpixels/fabric/")
    maven("https://maven.terraformersmc.com/releases/")
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")
    modImplementation("com.terraformersmc:modmenu:${property("mod_menu_version")}")

    modImplementation("xyz.dicedpixels:pixel:${property("pixel_version")}")
    include("xyz.dicedpixels:pixel:${property("pixel_version")}")
}

loom {
    accessWidenerPath = file("src/main/resources/$modId.accesswidener")
}

afterEvaluate {
    loom {
        runs {
            configureEach {
                vmArg("-javaagent:${configurations.compileClasspath.get().find { cls -> cls.name.contains("sponge-mixin") }}")
                vmArg("-XX:+AllowEnhancedClassRedefinition")
            }
        }
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }

    withType<JavaCompile> {
        options.release = 17
    }

    jar {
        from("LICENSE")
    }
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

signing {
    sign(tasks["remapJar"])
}

spotless {
    java {
        toggleOffOn()
        palantirJavaFormat()
        removeUnusedImports()
        importOrder("java", "javax", "", "net.minecraft", "com.mojang", "net.fabricmc", "xyz.dicedpixels")
        formatAnnotations()
    }

    kotlinGradle {
        ktlint()
    }

    json {
        target("src/**/*.json")
        gson()
    }
}

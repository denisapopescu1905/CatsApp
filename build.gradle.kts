buildscript{
    val kotlin_version by extra("1.9.0")
    repositories{
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies{
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("com.google.gms:google-services:4.4.1")
        val navVersion = "2.7.6"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }

}
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.6.10" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}
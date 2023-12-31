plugins {
    java
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.41")
    implementation("cn.hutool:hutool-all:5.8.22")
    implementation("org.apache.commons:commons-exec:1.3")
    implementation("org.xerial:sqlite-jdbc:3.43.2.1")
    implementation("com.baomidou:mybatis-plus-boot-starter:3.5.4")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

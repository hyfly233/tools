package com.example.demo;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@EnableAsync
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    //    @PostConstruct
    void onApplicationStart() throws IOException {
        log.info("-------- 复制 terraform 插件到./plugins 开始 --------");

        ClassPathResource pluginsResource = new ClassPathResource("plugins.zip");
        InputStream inputStream = pluginsResource.getInputStream();

        ZipUtil.unzip(inputStream, new File("./"), StandardCharsets.UTF_8);
        File file = new File("./plugins");

        if (!file.exists() || FileUtil.isEmpty(file)) {
            log.error("-------- 复制 terraform 插件到./plugins 失败 --------");
        } else {
            this.setFileExecutable(file);
            log.info("-------- 复制 terraform 插件到./plugins 结束 --------");
        }
    }

    void setFileExecutable(File file) {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File listFile : listFiles) {
                    setFileExecutable(listFile);
                }
            }
        } else {
            Set<PosixFilePermission> perms = new HashSet<>();
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            perms.add(PosixFilePermission.OWNER_EXECUTE);
            perms.add(PosixFilePermission.GROUP_READ);
            perms.add(PosixFilePermission.GROUP_WRITE);
            perms.add(PosixFilePermission.GROUP_EXECUTE);
            try {
                Path path = Paths.get(file.getAbsolutePath());
                Files.setPosixFilePermissions(path, perms);
            } catch (Exception e) {
                log.error("设置文件 (" + file.getName() + ") 权限失败", e);
            }
        }
    }
}

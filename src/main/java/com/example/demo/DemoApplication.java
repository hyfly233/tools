package com.example.demo;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@Log4j2
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    //    @PostConstruct
    void onApplicationStart() throws IOException {
        log.info("-------- 复制 terraform 插件到./plugins 开始 --------");

        ClassPathResource pluginsResource = new ClassPathResource("plugins/plugins.zip");
        InputStream inputStream = pluginsResource.getInputStream();

        ZipUtil.unzip(inputStream, new File("./"), StandardCharsets.UTF_8);
        File file = new File("./plugins");

        if (!file.exists() || FileUtil.isEmpty(file)) {
            log.error("-------- 复制 terraform 插件到./plugins 失败 --------");
        } else {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File listFile : listFiles) {
                    if (listFile.isDirectory()) {
                        continue;
                    }
                    listFile.setExecutable(true);
                }
            }
            log.info("-------- 复制 terraform 插件到./plugins 结束 --------");
        }
    }
}

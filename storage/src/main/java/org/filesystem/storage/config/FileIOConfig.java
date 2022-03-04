package org.filesystem.storage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class FileIOConfig {

    @Value("${spring.servlet.multipart.max-file-size}")
    String maxFileSize;
    @Value("${spring.servlet.multipart.max-request-size}")
    String maxRequestSize;
    @Value("${spring.servlet.multipart.location}")
    String uploadDirectory;


    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver commonsMultipartResolver() throws Exception {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(DataSize.parse(maxRequestSize).toBytes());
        commonsMultipartResolver.setMaxUploadSizePerFile(DataSize.parse(maxFileSize).toBytes());
        commonsMultipartResolver.setUploadTempDir(new FileSystemResource(uploadDirectory));
        commonsMultipartResolver.getFileItemFactory().setFileCleaningTracker(null); //Need to set this to null to avoid file deletion post file upload
        return commonsMultipartResolver;
    }
}

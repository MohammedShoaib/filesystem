package org.filesystem.storage.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.filesystem.storage.api.FileChunkRequest;
import org.filesystem.storage.dto.FileUploadDTO;
import org.filesystem.storage.exceptions.FileUploadException;
import org.filesystem.storage.util.FileUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
public class FileStorageService {
    private final FileUtil fileUtil;

    public FileStorageService(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    public void storeFile(MultipartFile file, FileUploadDTO fileUploadDTO) {
        final String uploadPath = fileUtil.getUploadPath(fileUploadDTO.getFileName(), fileUploadDTO.getFileId(), fileUploadDTO.getChunkId());
        try (final InputStream uploadedStream = file.getInputStream();
             final OutputStream out = new FileOutputStream(uploadPath)) {
            IOUtils.copy(uploadedStream, out);
        } catch (Exception e) {
            throw new FileUploadException("Error uploading file", e.getCause());
        }
    }

    public void deleteFile(String fileName, FileChunkRequest fileChunkRequest) {
        final String filePath = getUploadPath(fileName, fileChunkRequest);
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            log.info("File {} does not exist, moving on", filePath);
        }
    }

    public Resource getFile(String fileName, FileChunkRequest fileChunkRequest) {
        final String filePath = getUploadPath(fileName, fileChunkRequest);
        final Path root = Paths.get(filePath);
        try {
            Path file = root.resolve(filePath);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.info("Read File {} Error!", filePath);
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File Path Error: " + e.getMessage());
        }
    }

    private String getUploadPath(String fileName, FileChunkRequest fileChunkRequest) {
        return fileUtil.getUploadPath(fileName, fileChunkRequest.getFileId(), fileChunkRequest.getChunkID());
    }
}

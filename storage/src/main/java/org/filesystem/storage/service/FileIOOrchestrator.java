package org.filesystem.storage.service;

import org.filesystem.storage.api.FileChunkRequest;
import org.filesystem.storage.dto.FileUploadDTO;
import org.filesystem.storage.notification.FileUploadNotificationService;
import org.filesystem.storage.validator.FileValidator;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileIOOrchestrator {

    private final FileValidator fileValidator;
    private final FileStorageService fileStorageService;
    private final FileUploadNotificationService fileUploadNotificationService;

    public FileIOOrchestrator(FileValidator fileValidator,
                              FileStorageService fileStorageService,
                              FileUploadNotificationService fileUploadNotificationService) {
        this.fileValidator = fileValidator;
        this.fileStorageService = fileStorageService;
        this.fileUploadNotificationService = fileUploadNotificationService;
    }

    public void uploadFile(MultipartFile multipartFile, FileUploadDTO fileUploadDTO) {

        fileValidator.validateFile(multipartFile, fileUploadDTO);
        fileStorageService.storeFile(multipartFile, fileUploadDTO);
        fileUploadNotificationService.publishFileUploadNotification(fileUploadDTO);
    }

    public void deleteFile(String fileName, FileChunkRequest fileChunkRequest) {
        fileStorageService.deleteFile(fileName, fileChunkRequest);
    }

    public Resource getFile(String fileName, FileChunkRequest fileChunkRequest) {
        return fileStorageService.getFile(fileName, fileChunkRequest);
    }
}

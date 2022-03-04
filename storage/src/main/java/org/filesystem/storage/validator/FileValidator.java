package org.filesystem.storage.validator;

import org.filesystem.storage.api.FileChunkRequest;
import org.filesystem.storage.dto.FileUploadDTO;
import org.filesystem.storage.exceptions.FileEmptyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileValidator {

    public void validateFile (MultipartFile multipartFile, FileUploadDTO fileUploadDTO) {
        if (multipartFile.isEmpty()) {
            throw new FileEmptyException();
        }
    }
}

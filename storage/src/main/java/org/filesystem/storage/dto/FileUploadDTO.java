package org.filesystem.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileUploadDTO {
    private String fileName;
    private long fileId;
    private long chunkId;
}

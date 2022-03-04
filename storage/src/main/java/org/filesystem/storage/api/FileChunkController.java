package org.filesystem.storage.api;

import lombok.extern.slf4j.Slf4j;
import org.filesystem.storage.dto.FileUploadDTO;
import org.filesystem.storage.service.FileIOOrchestrator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;

@RestController("/file-chunk")
@Slf4j
public class FileChunkController {

    private final FileIOOrchestrator fileIOOrchestrator;

    public FileChunkController(FileIOOrchestrator fileIOOrchestrator) {
        this.fileIOOrchestrator = fileIOOrchestrator;
    }

    @PostMapping(path = "/{file-name}", consumes = {"multipart/form-data"})
    public ResponseEntity<String> handleUpload(@PathVariable("file-name") final String fileName,
                                               final FileChunkRequest fileChunkRequest,
                                               @RequestPart("file") MultipartFile file) {

        FileUploadDTO fileUploadDTO = new FileUploadDTO(fileName, fileChunkRequest.getFileId(), fileChunkRequest.getChunkID());
        fileIOOrchestrator.uploadFile(file, fileUploadDTO);
        return new ResponseEntity<>("Uploaded File Successfully", HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{file-name}")
    public ResponseEntity<String> deleteFileChunk(@PathVariable("file-name") final String fileName,
                                                  @RequestBody final FileChunkRequest fileChunkRequest) {

        fileIOOrchestrator.deleteFile(fileName, fileChunkRequest);
        return new ResponseEntity<>("Uploaded File Successfully", HttpStatus.ACCEPTED);
    }
}

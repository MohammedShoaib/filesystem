package org.filesystem.storage.notification;

import org.filesystem.storage.dto.FileUploadDTO;
import org.springframework.stereotype.Service;

/**
 * Class to notify replication module that a file has been uploaded and to instruct it to begin replication process
 * for the same.
 */
@Service
public class FileUploadNotificationService {

    public void publishFileUploadNotification(FileUploadDTO fileUploadDTO) {
        //TODO: notify replication module's listener via a spring event or via direct method call
    }
}

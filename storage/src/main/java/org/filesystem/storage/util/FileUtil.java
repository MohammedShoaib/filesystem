package org.filesystem.storage.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class FileUtil {

    @Value("${spring.servlet.multipart.location}")
    private String uploadDirectory;

    private static final String SEPARATOR = "-";


    public String getUploadPath(final String fileName, final long fileId, final long chunkId) {
        return uploadDirectory + "//" + fileId + SEPARATOR + chunkId + SEPARATOR + fileName;
    }

}

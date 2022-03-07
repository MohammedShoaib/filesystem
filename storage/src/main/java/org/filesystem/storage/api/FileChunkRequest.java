package org.filesystem.storage.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileChunkRequest {

    private long fileId;
    private long chunkID;

}

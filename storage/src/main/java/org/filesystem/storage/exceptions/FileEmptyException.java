package org.filesystem.storage.exceptions;

public class FileEmptyException extends RuntimeException {
    public FileEmptyException() {
        super("Empty file");
    }
}

package com.phantomartist.email.handler;

import java.io.IOException;
import java.nio.file.Path;

public interface FileHandler {

    /**
     * Returns a Path representing the next available filename on the given
     * baseFolder that represents a unique filename. (Prevents overwriting 
     * existing files with the same name).
     * 
     * @param baseFolder the base folder to write to
     * @param proposedFilename the proposed filename to write to
     * 
     * @return Path the path that can be written-to
     */
    Path getAvailablePath(String baseFolder, String proposedFilename);

    /**
     * Write byte array to a file.
     * 
     * @param path the path
     * @param content the file content to write
     * 
     * @throws IOException if write error occurs
     */
    void writeToFile(Path path, byte[] content) throws IOException;
}

package com.phantomartist.email.handler.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import com.phantomartist.email.handler.FileHandler;

public class FileHandlerImpl implements FileHandler {

    @Override
    public Path getAvailablePath(String baseFolder, String proposedFilename) {
        Path file = Paths.get(baseFolder, proposedFilename);
        while (file.toFile().exists()) {
            // Try to give the file a unique name if the proposed name is taken
            file = Paths.get(baseFolder, proposedFilename + Instant.now().toEpochMilli());
        }
        return file;
    }

    @Override
    public void writeToFile(Path path, byte[] content) throws IOException {
        
        Files.write(path, content);
    }

}

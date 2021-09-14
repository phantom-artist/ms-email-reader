package com.phantomartist.email;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.phantomartist.email.handler.FileHandler;
import com.phantomartist.email.handler.MessageHandler;
import com.phantomartist.email.wrapper.Attachment;
import com.phantomartist.email.wrapper.Message;

public class EmailProcessor {

    private String attachmentDownloadDirectory;
    private MessageHandler messageHandler;
    private FileHandler fileHandler;

    public EmailProcessor(
        final String attachmentDownloadDirectory,
        final MessageHandler messageHandler,
        final FileHandler fileHandler) {
        
        this.attachmentDownloadDirectory = attachmentDownloadDirectory;
        this.messageHandler = messageHandler;
        this.fileHandler = fileHandler;
    }

    /**
     * Get unread messages
     */
    public void run() {

        final List<Message> processed = new ArrayList<>();

        final List<Message> unreads = messageHandler.getUnread();
        for (Message unread : unreads) {
            // If no attachments, go to next message
            if (!unread.hasAttachments()) {
                processed.add(unread);
                continue;
            }

            final List<Attachment> attachments = unread.getAttachments();
            for (Attachment attachment : attachments) {

                try {
                    final byte[] bytes = attachment.getAttachmentBytes();
                    if (bytes != null) {
                        final Path localFile = 
                            fileHandler.getAvailablePath(attachmentDownloadDirectory, 
                            attachment.getFilename());
    
                        fileHandler.writeToFile(localFile, bytes);
                        Logger.logInfo("Wrote file " + localFile.toFile().getAbsolutePath());
                    }
                    processed.add(unread);

                } catch (IOException ioe) {
                    Logger.logError("Failed to process attachment " + 
                        attachment.getFilename(), ioe);
                }
            }
        }

        messageHandler.markAsRead(processed);
    }
}

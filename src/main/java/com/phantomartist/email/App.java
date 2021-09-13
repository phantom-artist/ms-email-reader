package com.phantomartist.email;

import java.util.Objects;

import com.phantomartist.email.authentication.AccessProvider;
import com.phantomartist.email.authentication.AuthenticationBuilder;
import com.phantomartist.email.handler.FileHandler;
import com.phantomartist.email.handler.MessageHandler;
import com.phantomartist.email.handler.impl.FileHandlerImpl;
import com.phantomartist.email.handler.impl.MessageHandlerImpl;

/**
 * Wrapper class for running the application.
 */
public class App {

    public static void main(String[] args) {

        if (args.length < 3) {
            throw new RuntimeException(
                "Program requires args <tenantId> <applicationId> <applicationSecret> (optional) <download folder>");
        }

        // Very simplistic way of passing sensitive info via program params.
        // There are probably other (better) ways of injecting these.
        final String tenantId = args[0];
        final String applicationId = args[1];
        final String applicationSecret = args[2];

        Objects.requireNonNull(tenantId, "tenantId cannot be null");
        Objects.requireNonNull(applicationId, "applicationId cannot be null");
        Objects.requireNonNull(applicationSecret, "applicationSecret cannot be null");

        // The directory to download attachments to (defaults to tmp.dir)
        final String attachmentsDownloadPath = 
            args[3] == null ? 
                System.getProperty("java.io.tmpdir") : 
                args[3];
        
        final AccessProvider accessProvider = 
            AuthenticationBuilder.createAccessProvider(
                tenantId, applicationId, applicationSecret);

        final MessageHandler messageHandler = new MessageHandlerImpl(accessProvider);
        final FileHandler fileHandler = new FileHandlerImpl();

        final EmailProcessor emailProcessor = 
            new EmailProcessor(
                attachmentsDownloadPath, 
                messageHandler,
                fileHandler);
        emailProcessor.run();
        
    }
}

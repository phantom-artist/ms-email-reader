package com.phantomartist.email.wrapper.impl;

import java.io.IOException;

import com.microsoft.graph.models.FileAttachment;
import com.microsoft.graph.requests.FileAttachmentRequestBuilder;
import com.phantomartist.email.Logger;
import com.phantomartist.email.authentication.AccessProvider;
import com.phantomartist.email.wrapper.Attachment;

/**
 * Attachment implementation
 *
 */
public class AttachmentImpl implements Attachment {

    private AccessProvider accessProvider;
    private String messageId;
    private com.microsoft.graph.models.Attachment msAttachment;
    private byte[] attachmentBytes;

    public AttachmentImpl(
        final AccessProvider accessProvider, 
        final String messageId, 
        com.microsoft.graph.models.Attachment msAttachment) {

        this.accessProvider = accessProvider;
        this.messageId = messageId;
        this.msAttachment = msAttachment;
    }

    @Override
    public String getFilename() {
        return msAttachment.name.replaceAll(" ", "_");
    }

    @Override
    public String getContentType() {
        return msAttachment.contentType;
    }

    @Override
    public long size() {
        return msAttachment.size == null ? 
            0 : Integer.toUnsignedLong(msAttachment.size);
    }

    /**
     * Get the attachment as a FileAttachment object to get at the raw bytes.
     * 
     * @throws IOException if attachment cannot be processed
     */
    @Override
    public byte[] getAttachmentBytes() throws IOException {

        if (attachmentBytes != null) {
            // return the cached bytes rather than making a new web call
            return attachmentBytes;
        }

        if ("#microsoft.graph.fileAttachment".equals(msAttachment.oDataType)) {

            final String attachmentRequestURL =
                accessProvider.getServiceClient()
                .me()
                .messages(messageId)
                .attachments(msAttachment.id)
                .buildRequest()
                .getRequestUrl()
                .toString();

            final FileAttachmentRequestBuilder fileRequestBuilder =
                new FileAttachmentRequestBuilder(
                    attachmentRequestURL, 
                    accessProvider.getServiceClient(),
                    null);

            final FileAttachment fileAttachment = 
                fileRequestBuilder.buildRequest().get();

            if (fileAttachment != null) {
                attachmentBytes = fileAttachment.contentBytes;
            }
            if (attachmentBytes == null) {
                Logger.logError("getAttachmentBytes() failed - null byte array", 
                    new Exception("Attachment file bytes is null"));
            }
        }

        return attachmentBytes;
    }
}

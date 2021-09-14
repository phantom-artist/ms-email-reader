package com.phantomartist.email.wrapper.impl;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.http.HttpException;

import com.microsoft.graph.core.BaseClient;
import com.phantomartist.email.Logger;
import com.phantomartist.email.authentication.AccessProvider;
import com.phantomartist.email.wrapper.Attachment;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
     * Example URL for getting attachment bytes
     * https://docs.microsoft.com/en-us/graph/api/attachment-get?view=graph-rest-1.0&tabs=java
     * 
     * GET https://graph.microsoft.com/v1.0/me/messages/AAMkADA1M-zAAA=/attachments/AAMkADA1M-CJKtzmnlcqVgqI=/$value
     * 
     * @throws IOException if attachment cannot be processed
     */
    @Override
    public byte[] getAttachmentBytes() throws IOException {

        if (attachmentBytes != null) {
            // return the cached bytes rather than making a new web call
            return attachmentBytes;
        }

        // Get the access token required for manual API call
        final String accessToken = accessProvider.getAccessToken();

        // See https://docs.microsoft.com/en-us/graph/auth-v2-user
        final Request request = new Request.Builder().url(
            BaseClient.DEFAULT_GRAPH_ENDPOINT +
            "/me/messages/" +
            messageId +
            "/attachments/" +
            msAttachment.id +
            "/$value") // $value accesses the raw bytes
            .addHeader("Authorization", "Bearer " + accessToken) // Authorization header
            .addHeader("Host", "graph.microsoft.com")
            .build();

        final OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == HttpURLConnection.HTTP_OK) {
                attachmentBytes = response.body().bytes();
            } else {
                Logger.logError("getAttachmentBytes() failed - HTTP Response " + 
                    response.code() + ": Detail " + response.message(), 
                    new HttpException());
                attachmentBytes = null;
            }
        }
        return attachmentBytes;
    }
}

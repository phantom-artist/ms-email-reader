package com.phantomartist.email.wrapper;

import java.io.IOException;

public interface Attachment {

    String getFilename();

    String getContentType();

    long size();

    /**
     * To get at the raw bytes of an attachment we have to craft a raw Graph API 
     * call to get the $value  
     * e.g. GET https://graph.microsoft.com/v1.0/me/messages/AAMkADA1M-zAAA=/attachments/AAMkADA1M-CJKtzmnlcqVgqI=/$value
     * This should be used with {@link #getContentType()} to determine what
     * to reconstruct the bytes as.
     *
     * @return byte[] the byte array representation of the attachment
     */
    byte[] getAttachmentBytes() throws IOException;
}

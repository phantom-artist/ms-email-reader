package com.phantomartist.email.util;

import com.phantomartist.email.Logger;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class ByteUtil {

    /**
     * Decode a byte array that has been encoded in Base64
     * 
     * @param base64Encoded the byte array
     * 
     * @return byte[] the bytes
     */
    public static byte[] decodeBase64Bytes(final byte[] base64Encoded) {
        if (base64Encoded == null) {
            return null;
        }

        byte[] decodedBytes = null;
        try {
            decodedBytes = Base64.decode(base64Encoded);
        } catch (Base64DecodingException e) {
            Logger.logError("Error decoding attachment bytes", e);
        }
        return decodedBytes;
    }

    private ByteUtil() {
    }
}

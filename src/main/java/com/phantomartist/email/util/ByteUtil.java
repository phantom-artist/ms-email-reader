package com.phantomartist.email.util;

import java.util.Base64;

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

        return Base64.getDecoder().decode(base64Encoded);
    }

    private ByteUtil() {
    }
}

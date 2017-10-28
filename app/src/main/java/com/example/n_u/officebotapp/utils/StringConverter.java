package com.example.n_u.officebotapp.utils;

/**
 * Created by usman on 11/2/2016.
 */

public class StringConverter {
    private final static char[] hexArray = {
            '0', '1', '2',
            '3', '4', '5',
            '6', '7', '8',
            '9', 'A', 'B',
            'C', 'D', 'E', 'F'
    };

    public static String bytesToHex(final byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        final int bytes_length = bytes.length;// Moved  bytes.length call out of the loop to local variable bytes_length
        for (int j = 0; j < bytes_length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}

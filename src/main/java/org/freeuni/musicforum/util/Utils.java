package org.freeuni.musicforum.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Utils {
    private Utils() {}

    private static final String HASH_ALGORITHM = "SHA-256";

    private static String hexToString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (int curByte : bytes) {
            int val = curByte;
            val = val & 0xff;
            if (val < 16) builder.append('0');
            builder.append(Integer.toString(val, 16));
        }
        return builder.toString();
    }

    public static String hashText(String text) {
        String hashed = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            hashed = hexToString(digest.digest(text.getBytes(StandardCharsets.UTF_8)));
        }
        catch(NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return hashed;
    }

}

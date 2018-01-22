package com.yn.user.cliantapplication.model.backend;

import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by USER on 19/11/2017.
 */

public class SHA_256_Helper {
    public static String getHash256String(String password,long salt )throws Exception {

        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        (password+=salt).trim();
        (password).trim();
        sha256.update(password.getBytes());

        byte byteData[] = sha256.digest();

        //convert the byte to hex format method
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            stringBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();

    }
    public static long generateSalt( ) {
        Random random = new Random();
        long tempSalt = random.nextLong();

        while (tempSalt == 0) {
            tempSalt = random.nextLong();

        }
        return tempSalt;
    }
    }


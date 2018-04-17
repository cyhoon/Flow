package kr.hs.dgsw.flow.helper;

import java.math.BigInteger;
import java.security.MessageDigest;

public class encryption {
    public static String getSHA512(String input) {
        String toReturn = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(input.getBytes("utf-8"));
            toReturn = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toReturn;
    }
}

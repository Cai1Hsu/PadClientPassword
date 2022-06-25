package padclientpassword;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    public static final String AES = "AES";
    public static final String CIPHER = "AES/CBC/PKCS5Padding";
    public static final String CRYPTO = "Crypto";
    public static final String SHA1 = "SHA1PRNG";

    public static String encrypt(String keys, String content) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            SecureRandom sr = new SecureRandom();
            sr.setSeed(keys.getBytes());
            kgen.init(128, sr);
            IvParameterSpec iv = new IvParameterSpec("fedcba9876543210".getBytes());
            SecretKeySpec key = new SecretKeySpec("0123456789abcdef".getBytes(), AES);
            Cipher cipher = Cipher.getInstance(CIPHER);
            byte[] byteContent = content.getBytes();
            cipher.init(1, key, iv);
            return parseByte2HexStr(cipher.doFinal(byteContent));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            SecureRandom sr = new SecureRandom();
            sr.setSeed(password.getBytes());
            kgen.init(256, sr);
            SecretKeySpec key = new SecretKeySpec(kgen.generateKey().getEncoded(), AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(2, key);
            return new String(cipher.doFinal(parseHexStr2Byte(content)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & -1);
            if (hex.length() == 1) {
                hex = String.valueOf('0') + hex;
            }
            sb.append(hex.toLowerCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[(hexStr.length() / 2)];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, (i * 2) + 1), 16);
            result[i] = (byte) ((high * 16) + Integer.parseInt(hexStr.substring((i * 2) + 1, (i * 2) + 2), 16));
        }
        return result;
    }
}
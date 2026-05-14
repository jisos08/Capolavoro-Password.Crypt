import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.Base64;

public class Crypto {
    public static SecretKey getKey(String password, String salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] key = f.generateSecret(spec).getEncoded();
        return new SecretKeySpec(key, "AES");
    }

    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(c.doFinal(data.getBytes()));
    }

    public static String decrypt(String enc, SecretKey key) throws Exception {
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decoded = Base64.getDecoder().decode(enc);
        return new String(c.doFinal(decoded));
    }
}
import java.nio.file.*;
import javax.crypto.SecretKey;

public class Storage {
    private static final String FILE = "data/passwords.dat";
    private static final String SALT = "12345678";

    public static void save(String content, String master) throws Exception {
        SecretKey key = Crypto.getKey(master, SALT);
        String encrypted = Crypto.encrypt(content, key);
        Files.writeString(Path.of(FILE), encrypted);
    }

    public static String load(String master) throws Exception {
        if (!Files.exists(Path.of(FILE)))
            return "";
        SecretKey key = Crypto.getKey(master, SALT);
        String encrypted = Files.readString(Path.of(FILE));
        return Crypto.decrypt(encrypted, key);
    }
}
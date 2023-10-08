package MaHoa;

import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class DES_AES {
    public static final int AES_128 = 128;
    public static final int AES_192 = 192;
    public static final int AES_256 = 256;
    
    String key; // User defined key;
    byte[] DES_key = null;
    int AES_KeyLen = 128;
    byte[] AES_key = null;

    //Constructor
    public DES_AES(String aKey) {
        key = aKey;
        DES_key = DES_createKey();
        AES_key = AES_createKey();
    }

    public DES_AES(String aKey, int AES_KeyLen) {
        key = aKey;
        // Cập nhật AES_KeyLen
        if(AES_KeyLen == AES_192) this.AES_KeyLen = AES_192;
        else if (AES_KeyLen == AES_256) this.AES_KeyLen = AES_256;
        DES_key = DES_createKey();
        AES_key = AES_createKey();
    }

    // Tạo DES key từ String key

    private byte[] DES_createKey() {
        try {
            byte[] keyBytes = key.getBytes("UTF-8");
            DES_key = Arrays.copyOf(keyBytes, 8); // Lấy 8 bytes, 64 bits.
            return DES_key;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    // Tạo AES key từ String key
    private byte[] AES_createKey() {
        try { // Tạo Key cho việc mã hoá/ giải mã AES
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] keyBytes = key.getBytes("UTF-8");
            keyBytes = sha.digest(keyBytes);
            AES_key = Arrays.copyOf(keyBytes, AES_KeyLen/8);
            return AES_key;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    // Mã hoá DES một chuỗi nguồn
    public String DES_enorypt(String source) {
        try {
            // Tạo lớP chứa kháo theo thuật toán DES
            SecretKeySpec skeySpec = new SecretKeySpec(DES_key, "DES");
            // Tạo đối tượng mã hoá// giải mã dùng giải thuật DES
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
            // Khởi tạo cơ chế mã hoá với khoá đã có
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            // Mã hoá chuỗi nguồn thành mảng bytes
            byte[] byteEncoded = cipher.doFinal(source.getBytes());
            // Chuyển mảng byte kết quả thành chuỗi dùng bảng mã Base64
            String encodedStr = Base64.getEncoder().encodeToString(byteEncoded);
            return encodedStr;
        } catch (Exception e) {
            System.out.println("\n DES Encrypt: " + e.toString());
        }
        return null;
    }

    // Giải mã DES một chuỗi base64
    public String DES_decrypt(String encryptedStr) {
        try {
            // Tạo lớp chứa kháo theo thuật toán DES
            SecretKeySpec skeySpec = new SecretKeySpec(DES_key, "DES");
            //Tạo đối tưỢng mã hoá/ giải mã dùng giải thuật DES
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
            // Khởi tạo cơ chế giải mã với khoá đã có
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            // Đổi chuỗi encryptedStr dạng Base64 thành byte[]
            byte[] byteEncrypted = Base64.getDecoder().decode(encryptedStr);
            // Giải mã byteDecrypted thành byte[] ban đầu
            byte[] byteDecrypted = cipher.doFinal(byteEncrypted);
            // Chuyển kết quả dạng byte[] thành String
            return new String(byteDecrypted);
        } catch (Exception e) {
            System.out.println("\n DES Decrypt: " + e);
        }
        return null;
    }

    // Mã hoá chuỗi nguồn dùng AES, Advanced Encryption Standard
    public String AES_encrypt(String source) {
        try {
            // Tạo đối tượng cho key và đối tượng mã hoá
            SecretKeySpec secretKey = new SecretKeySpec(AES_key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PRCS5Padding");
            // Khởi tạo cơ chế mã hoá bí mật đã có
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            // Lấy kết quả mã hoá dạng bytes
            byte[] bytes = cipher.doFinal(source.getBytes("UTF-8"));
            // Chuyển kết quả thành Base64 string
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            System.out.println("\n AES Encrypt: " + e.toString());
        }
        return null;
    }

    // Giải mã AES, Advanced Encryption Standard
    public String AES_descrypt(String encryptedStr) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(AES_key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PRCS5PADDING");
            // Khởi tạo cơ chế giải mã với mã bí mật đã có
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            // Đổi chuỗi đã mã háo Base64 thành mảng bytes
            byte[] bytes = Base64.getDecoder().decode(encryptedStr);
            // giải mã. Kết quả dạng bytes
            byte[] result = cipher.doFinal(bytes);
            return new String(result); // Chuyển kết quả thành string
        } catch (Exception e) {
            System.out.println("\n AES Decrypt: " + e.toString());
        }
        return null;
    }

    //Test
    public static void main(String[] args) {
        // Test DES
        String myKey = "My Honey! 123"; // 8 ký số --> 64 bits
        DES_AES myCipher = new DES_AES(myKey);
        String src = "I love you more than I can say";
        String DES_enoryptStr = myCipher.DES_enorypt(src);
        String DES_decryptStr = myCipher.DES_decrypt(DES_enoryptStr);
        System.out.println("DES Encrypted Str: " + DES_enoryptStr);
        System.out.println("DES Decrypted Str: " + DES_decryptStr);
        // Test AES 192 bit
        DES_AES myCipher2 = new DES_AES(myKey, DES_AES.AES_192);
        String AES_encryptStr = myCipher.AES_encrypt(src);
        String AES_descryptStr = myCipher.AES_descrypt(AES_encryptStr);
        System.out.println("AES Encrypted Str: " +AES_encryptStr);
        System.out.println("AES Decrypted Str: " + AES_descryptStr);
    }
}

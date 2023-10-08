package MaHoaKhongDoiXung;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSA_MyCipher {
    String publickeyFname = null;
    String privatekeyFname = null;

    //Mã hoá dât bằng public key để trong file
    public String encode(String publickeyFName, String src) {
        try{
            byte[] b = null;
            // Đọc public ket từ dile vào mảng bytes
            if(this.publickeyFname == null) {
                this.publickeyFname = publickeyFName;
                FileInputStream fis = new FileInputStream(publickeyFName);
                b = new byte[fis.available()];
                fis.read(b);
                fis.close();
            }
            // Tạo public key
            X509EncodedKeySpec spec = new X509EncodedKeySpec(b);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = factory.generatePublic(spec);
            // Mã Hoá dữ liệu
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, publicKey);
            byte encryptOut[] = c.doFinal(src.getBytes());
            String strEncrypt = Base64.getEncoder().encodeToString(encryptOut);
            return strEncrypt;
        } catch(Exception e) {
            System.err.println(e);
        }
        return "";
    }

    //Cách giải mã private key
    public String decode(String privateFName, String encodedStr) {
        String decodedStr = "";
        try {
            byte[] b = null;
            if(this.privatekeyFname == null) {
                this.privatekeyFname = privateFName;
                // ĐỌc file chứa private key đưa vào mảng b
                FileInputStream fis = new FileInputStream(this.privatekeyFname);
                b = new byte[fis.available()];
                fis.read(b);
                fis.close();
            }
            // Tạo private key từ mảng b
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(spec);
            // Giải mã dữ liệu
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptOut = c.doFinal(Base64.getDecoder().decode(decodedStr));
            decodedStr = new String(decryptOut);
        } catch (Exception e) {
            System.err.println(e);
        }
        return decodedStr;
    }

    //Test
    public static void main(String[] args) {
        String pkFname = "./RSA_Data/publickey01.rsa";
        String prKname ="./RSA_Data/privatekey01.rsa";
        RSA_MyCipher cipher = new RSA_MyCipher();
        String src = "Yêu em biết để vào đâu.";
        System.out.println("SRC: " + src);
        String encodedStr = cipher.encode(pkFname, src);
        System.out.println("Encoded: " + encodedStr);
        String decodedStr = cipher.decode(prKname, encodedStr);
        System.out.println("Decoded: " + decodedStr);
    }
}

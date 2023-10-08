package MaHoaKhongDoiXung;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class RSA_KeyGen {
    //Tạo file bằng cách xoá file cũ nếu file này tồn tại rồi
    private static File createNewFile(File file) throws IOException {
        if(!file.exists()) file.createNewFile();
        else {
            file.delete();
            file.createNewFile();
        }
        return file;
    }

    //Sinh các key và lưu file
    //Độ dài khoá quyết định múc an toàn của khoá, khoá càng dài càng khó
    // dò (bẻ). Độ dài khoá 20248 được khuyên dùng
    public static void keyGen(int keyLen, String pulicKeyFname, String privateKeyname) {
        try {
            SecureRandom sr = new SecureRandom(); // Khởi tạo cơ chế ngẫu nhiên
            //KhởI tạo đối tượng sinh cặp khoá
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(keyLen, sr);
            KeyPair kp = kpg.genKeyPair(); // Lấy cặp khoá
            PublicKey publicKey = kp.getPublic(); // Lấy ra public key
            PrivateKey privatekey = kp.getPrivate(); // Lấy ra private key
            // Tạo 2 file sẵn sàng cho việc ghi
            File pKFile = createNewFile(new File(pulicKeyFname));
            File prK = createNewFile(new File(privateKeyname));
            //Lưu các keys vào file
            FileOutputStream fos = new FileOutputStream(pKFile);
            fos.write(publicKey.getEncoded());
            fos.close();
            System.out.println("Generate key successfully");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    //Test 
    public static void main(String[] args) {
        String pkFname = "./RSA_Data/publickey01.rsa";
        String prkFname = "./RSA_Data/privatekey01.rsa";
        int len = 1024 * 3;
        keyGen(len, pkFname, prkFname);
    }
}

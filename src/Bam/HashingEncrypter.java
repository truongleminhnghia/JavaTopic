package Bam;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingEncrypter {
    // Declare names of hasing Algorithms
    public static final String MD2 = "MD2";
    public static final String MD5 = "MD5";
    public static final String SHA_1 = "SHA_1";
    public static final String SHA_224 = "SHA_224";
    public static final String SHA_256 = "SHA_256";
    public static final String SHA_384 = "SHA_384";
    public static final String SHA_512 = "SHA_512";

    //Declare Unicode Character set
    public static final Charset UTF_8 = StandardCharsets.UTF_8;
    //Get encrypted result from byte-based input
    public static byte[] getDigest(String algorithmName, byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input); // create encrypted result
        return result;
    }

    // Convert a byte array to a hexadecimal string
    private static String byterToHex(byte[] bytes) {
        //Using StringBuilder for conting digits
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) { // converting each byte to 2 hexa digits
            String apart = String.format("%02x", b);
            sb.append(apart); // add to the result
        }
        return sb.toString();
    }

    //Encrypt a source string using a hasing method
    public static String getHexaDigest(String algorithmName, String src) {
        //Convert unicode source string to bytes
        byte[] inoutBytes = src.getBytes(UTF_8);
        //create encrypting byte from input bytes
        byte[] encrypteBytes = getDigest(algorithmName, inoutBytes);
        //Convert encrypting byte to hexadecimal string
        return byterToHex(encrypteBytes);
    }

    private static void output(String msg, String data) {
        System.out.println(msg +":" + data + "\nLen = " + data.length());
    }

    //Test
    public static void main(String[] args) {
        String userPwd = "12abc@89";
        int len = userPwd.length();
        output("Source", userPwd);
        String MD2Str = HashingEncrypter.getHexaDigest(MD2, userPwd);
        output("MD2", MD2Str);
        String MD5Str = HashingEncrypter.getHexaDigest(MD5, userPwd);
        output("MD5", MD5Str);
        String SHA1Str = HashingEncrypter.getHexaDigest("SHA_1", userPwd);
        output("SHA_1", SHA1Str);
        String SHA224Str = HashingEncrypter.getHexaDigest("SHA_224", userPwd);
        output("SHA_224", SHA224Str);
        String SHA256Str = HashingEncrypter.getHexaDigest("SHA_256", userPwd);
        output("SHA_256", SHA256Str);
        String SHA384Str = HashingEncrypter.getHexaDigest("SHA_384", userPwd);
        output("SHA_385", SHA384Str);
        String SHA512Str = HashingEncrypter.getHexaDigest("SHA_512", userPwd);
        output("SHA_512", SHA512Str);
    }
}

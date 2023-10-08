package MaHoaKhongDoiXung;

// Lớp đơn giản giúp hiểu RSA
public class MaHoaNoDoiXung {
    int N; // Số lượng mã có thể mã hoá và giải mã
    int E;
    int D;

    // Khởi tạo cơ chế mã hoá/ giải mã với 2 số nguyên P, Q
    public MaHoaNoDoiXung(int P, int Q) {
        N = P * Q; // Số lượng mã được hỗ trợ.
        int pN = (P - 1) * (Q - 1); // pN= 60, pN là số không nguyên tố
        // Ở đây ko dò tìm D và E mà gán trị luôn
        E = 13; // Tìm e là 1 số nguyên tố
        D = 37; // TÌm D để (D*E) % pN -> 1
    }

    // TÍnh (x^power)mod m với 3 trị đều là số > 0
    // x^power có thể trần trị integer. Giải thuật tránh tràn trị
    private int modulo(int x, int power, int m) {
        int result = x % m;
        for (int i = 0; i < power; i++) {
            result = (result * x) % m;
        }
        return result;
    }

    // Mã hoá 1 chuỗI trả về chuỗi đã mã hoá
    public String encrypt(String src) {
        char[] srcChars = src.toCharArray();
        int L = srcChars.length;
        char[] encodeChars = new char[L];
        for (int i = 0; i < L; i++) {
            encodeChars[i] = (char) modulo((int) srcChars[i], E, N);
        }
        return new String(encodeChars);
    }

    //Giải mã chuỗi đã mã hoá trả về chuỗi ban đầu
    public String decrypt(String encodeStr) {
        char[] encodeChars = encodeStr.toCharArray();
        int L = encodeChars.length;
        char[] srcChars = new char[L];
        for (int i = 0; i < L; i++) {
            srcChars[i] = (char) modulo((int) encodeChars[i], D, N);
        }
        return new String(srcChars);
    }

    //Test

    public static void main(String[] args) {
        int P = 7;
        int Q = 11;

        MaHoaNoDoiXung sRSA = new MaHoaNoDoiXung(P, Q);
        String S = "1234567890ABCDEF"; // N = 77 -> chỉ chấp nhận mã từ 0 đến 76.
        System.out.println("Initial: " + S);
        String encodeStr = sRSA.encrypt(S);
        System.out.println("Encoded string: " + encodeStr);
        String decodeStr = sRSA.decrypt(encodeStr);
        System.out.println("Decoded string: " + decodeStr);
    }
}

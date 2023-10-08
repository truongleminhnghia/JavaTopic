/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MaHoa;

/**
 *
 * @author Administrator
 */
public class MaHoaDoiXung {
    private int TABLE_SIZE = 65536; // Số ký tự Unicdoe;

    int nPosition; // Số vị trí sẽ dịch khi9 mã hoá. > 0: tới <0: lui

    public MaHoaDoiXung(int nPosition) {
        this.nPosition = nPosition;
    }

    // Dịch một kí tự n bị trí. nPos>0 dịch tới, nPos<0 dịch lùi
    private char shift(char c, int nPos) {
        int charCode = (int) c + nPos;
        // -5%10 --> -5, Bổ sung TABLE_SIZE để né đặc điểm này của op. %
        if (charCode < 0)
            charCode += TABLE_SIZE;
        return (char) (charCode % TABLE_SIZE);
    }

    // Mã hoá một chuỗi nhập trả về chuỗi đã mã hoá.
    public String encode(String src) {
        String result = "";
        for (int i = 0; i < src.length(); i++) {
            result += shift(src.charAt(i), nPosition);
        }
        return result;
    }

    // Giải mã hoá một chuỗi đã mã háo , trả về chuỗi ban đầu

    public String decode(String encodedStr) {
        String src = "";
        for (int i = 0; i < encodedStr.length(); i++) {
            src += shift(encodedStr.charAt(i), -nPosition);
        }
        return src;
    }

    // Mã hoá phức tạp với số bước dịch thay đổi. Trả về chuỗi đã mã háo
    // Ký tự thứ nhất sẽ dịch theo quy đinh (nPosition vị trí).
    // Ký tự thứ hai lại dịch với nPos = mã của ký tự thứ nhất.
    // Ký tự thứ ba lại dịch nới nPos = mã của ký tự thứ hai.
    // Và cứ thế

    public String encodeComplex(String src) {
        String result = "";
        char curChar = src.charAt(0); // dịch lấy ký tự đầu
        result += shift(curChar, nPosition);
        int newShift; // số vị trí dịch các ký tự sau
        char perChar;

        // dịch từng ký tự với bước dịch thay đổi
        for (int i = 1; i < src.length(); i++) {
            perChar = curChar;
            newShift = (int) perChar;
            curChar = src.charAt(i);
            result += shift(curChar, newShift);
        }
        return result;
    }

    // Giải mã theo cơ chế dịch phức tạp ở trên. Trả về chuỗi ban đầu
    public String decodeCompex(String encodeStr) {
        String result = "";
        char curChar = shift(encodeStr.charAt(0), -nPosition);
        result += curChar;
        // dịch ngược từng ký tự với bước dịch thay đổi
        // số bước dịch của các ký tự sau là mã của ký tự đã giải mã trước đó
        int newShift;
        for(int i = 1; i < encodeStr.length(); i++) {
            newShift = (int) curChar; // bước dịch mớI
            curChar = shift(encodeStr.charAt(i), -newShift);
            result += curChar;
        }
        return result;
    }

    // TEST /////

    public static void main(String[] args) {
        String src = "ABCXYS nghịch ngợm";
        int nPosition = 3;
        MaHoaDoiXung shifter = new MaHoaDoiXung(nPosition);
        //Test simple symmetric method

        System.out.println("Simple symmetric encoding and decoding:");
        String encodeStr = shifter.encode(src);
        String decodeStr = shifter.decode(encodeStr);
        System.out.println("Source string: " + src);
        System.out.println("Encoded string: " +encodeStr);
        System.out.println("Decoded string: " + decodeStr);
        //Test a complex symmetric method
        System.out.println("Acomplex symmetric encoding and decoding:");
        encodeStr = shifter.encodeComplex(src);
        decodeStr = shifter.decodeCompex(encodeStr);
        System.out.println("sOURCE string: " +src);
        System.out.println("Encoded string: " + encodeStr);
        System.out.println("Decoded string: " + decodeStr);
    }
 }

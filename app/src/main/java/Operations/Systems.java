package Operations;

import java.util.ArrayList;

public class Systems {
    public static String from_p_to_q(String number, int p, int q){
        if ((p<1)||(p>16)||(q<1)||(q>16))
            throw new IllegalStateException("Недопустимое основание системы счисления!\n");
        return from_decimal(to_decimal(number,p), q);
    }
    private static int to_decimal(String number, int p){
        ArrayList<Integer> n = new ArrayList<>();
        int numb = 0;
        int c = 0;
        for (int i = 0; i < number.length(); ++i){
            double pow = Math.pow(p, number.length() - 1 - i);
            switch (number.charAt(i)) {
                case '0': c = 0; break;
                case '1': c = 1; break;
                case '2': c = 2; break;
                case '3': c = 3; break;
                case '4': c = 4; break;
                case '5': c = 5; break;
                case '6': c = 6; break;
                case '7': c = 7; break;
                case '8': c = 8; break;
                case '9': c = 9; break;
                case 'A': c = 10; break;
                case 'B': c = 11; break;
                case 'C': c = 12; break;
                case 'D': c = 13; break;
                case 'E': c = 14; break;
                case 'F': c = 15; break;
                default: throw new IllegalStateException("Неверная запись!\n");
            }
            if (c >= p)
                throw new IllegalStateException("Неверное число или система счисления!\n");
            numb+=c*pow;
        }

        return numb;
    }
    private static String from_decimal(int number, int q){
        ArrayList<Integer> n = new ArrayList<>();
        int res = 0;
        while (number!=0) {
            n.add((number%q));
            number/=10;
        }
        StringBuilder str = new StringBuilder();
        for (int i = n.size()-1; i >=0; --i){
            switch (n.get(i)){
                case 10: str.append("A"); break;
                case 11: str.append("B"); break;
                case 12: str.append("C"); break;
                case 13: str.append("D"); break;
                case 14: str.append("E"); break;
                case 15: str.append("F"); break;
                default: str.append(n.get(i));
            }

        }
        return str.toString();

    }
}

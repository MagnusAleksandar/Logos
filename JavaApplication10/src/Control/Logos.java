package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Logos {
    private static Set<Character> uniqueChars = new TreeSet<>();

    private static ArrayList<String> v = new ArrayList();
    private static ArrayList<String> opers = new ArrayList();
    private static ArrayList<String> vls = new ArrayList();
    private static ArrayList<Character> ch = new ArrayList<>(uniqueChars);
    private static ArrayList<Variable> vr = new ArrayList();
    // private static Variable vo = new Variable("", ' ');

    public static void arrayMaker(String op) {
        ArrayList<String> t = new ArrayList();
        String uh = "", f;
        int ini = 0, fin = 0, cont = 1;
        for (int i = 0; i < op.length(); i++) {
            if (op.charAt(i) != ')')
                uh = uh.concat(String.valueOf(op.charAt(i)));
            else {
                t.add(uh);
                uh = "";
            }
        }
        if (uh != "")
            t.add(uh);
        for (int j = 0; j < t.size(); j++) {
            cont = 0;
            f = "";
            uh = t.get(j);
            for (int c = 1; c < uh.length(); c++) {
                if (uh.charAt(c - 1) != '(')
                    f = f.concat(String.valueOf(uh.charAt(c - 1)));
                else {
                    opers.add(f);
                    f = "";
                }
                cont = c;
            }
            f = f.concat(String.valueOf(uh.charAt(cont)));
            opers.add(f);
        }
        System.out.println(opers);
    }

    public static int powCalc(String op) {
        int s;

        for (int i = 0; i < op.length(); i++) {
            if (op.charAt(i) != '(' && op.charAt(i) != ')' && op.charAt(i) != '+' && op.charAt(i) != '*') {
                uniqueChars.add(op.charAt(i));
            }
        }
        // uniqueChars.toArray();
        s = uniqueChars.size();
        return s;
    }

    public static void valAddr(String op) {
        String val = "";
        int pow = (int) Math.pow(2, powCalc(op)), powh = pow / 2, pos = 0;
        for (int c = 0; c < powCalc(op); c++) {
            val = "";
            for (int i = 0; i < powh; i++) {
                pos = i;
                val = val.concat(String.valueOf(1));
            }
            powh /= 2;
            for (int j = pos + 1; j < pow; j++) {
                val = val.concat(String.valueOf(0));
            }
            pow /= 2;
            if (c > 0)
                for (int k = 1; k <= c; k++) {
                    val = val.concat(val);
                }
            v.add(val);
        }
        System.out.println(v);
    }

    public static char plusTimes(char o, char n1, char n2) {
        char ent = ' ';
        switch (o) {
            case '+':
                if (n1 == '1' || n2 == '1')
                    ent = '1';
                else
                    ent = '0';
                break;
            case '*':
                if (n1 == '1' && n2 == '1')
                    ent = '1';
                else
                    ent = '0';
                break;
            default:
                System.out.println("Simbolo no valido");
                break;
        }
        return ent;
    }

    public static void parenth() {
        String va1, va2, test, res;
        char ca, cb, cc, r;
        for (int i = 0; i < opers.size(); i++) {
            res = "";
            va1 = "";
            va2 = "";
            test = opers.get(i);
            if (test.length() > 1) {
                for (int j = 0; j < vr.size(); j++) {
                    ca = test.charAt(0);
                    cb = vr.get(j).getNom();
                    cc = opers.get(i).charAt(2);
                    if (ca == cb)
                        va1 = vr.get(j).getVals();
                    if (cc == cb)
                        va2 = vr.get(j).getVals();
                }
                if (va1 != "" || va2 != "") {
                    for (int c = 0; c < va1.length(); c++) {
                        r = plusTimes(test.charAt(1), va1.charAt(c), va2.charAt(c));
                        res = res.concat(String.valueOf(r));
                    }
                    vls.add(res);
                }
            } else if (test.length() < 1) {
                opers.remove(test);
                i--;
            }
        }
        System.out.println(vls);
        // System.out.println(opers.size());
    }

    public static void finalOp() {
        String rfin = "";
        char r;
        for (int i = 1; i <= opers.size(); i++) {
            if (opers.get(i).length() == 1) {
                for (int j = 0; j < vls.get(i - 1).length(); j++) {
                    r = plusTimes(opers.get(i).charAt(0), vls.get(i).charAt(j), vls.get(i - 1).charAt(j));
                    rfin = rfin.concat(String.valueOf(r));
                }
                i = +2;
            }
        }
        System.out.println(rfin);
    }

    public static void assignVar() {
        String a = "";
        char c = ' ';
        ArrayList<Character> caux = new ArrayList<>(uniqueChars);
        ch = caux;
        System.out.println(ch);

        for (int i = 0; i < v.size(); i++) {
            a = v.get(i);
            c = ch.get(i);

            Variable vo = new Variable();
            vo.setNom(c);
            vo.setVals(a);
            vr.add(vo);
        }
    }

}

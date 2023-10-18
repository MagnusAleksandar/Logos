package Control;

import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Logos {
    private static Set<Character> uniqueChars = new TreeSet<>();

    private static ArrayList<String> v = new ArrayList();
    private static ArrayList<String> opers = new ArrayList();
    private static ArrayList<String> bigPar = new ArrayList();
    private static ArrayList<String> vls = new ArrayList();
    private static ArrayList<Character> ch = new ArrayList<>(uniqueChars);
    private static ArrayList<Variable> vr = new ArrayList();
    private static ArrayList<Operacion> ops = new ArrayList();
    private static ArrayList<String> os = new ArrayList();
    private static ArrayList<Operacion> bigRes = new ArrayList<>();

    public static boolean containsAny(String str) {
        String frb = "+*()[]";
        for (char c : frb.toCharArray()) {
            if (str.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }

    public static void arrayMaker(String op) {
        ArrayList<String> t = new ArrayList();
        ArrayList<String> tb = new ArrayList();
        String uh = "", uhb = "", f;
        int cont = 1;
        boolean bneg = false;
        for (int i = 0; i < op.length(); i++) {
            if (op.charAt(i) == '-')
                bneg = true;
            if ((op.charAt(i) == '(' || op.charAt(i) == '[') && bneg)
                bneg = false;
            if (op.charAt(i) != ']') {
                uhb = uhb.concat(String.valueOf(op.charAt(i)));
            } else {
                tb.add(uhb);
                uhb = "";
            }
            if (!bneg) {
                if (op.charAt(i) != ')')
                    uh = uh.concat(String.valueOf(op.charAt(i)));
                else {
                    t.add(uh);
                    uh = "";
                }
            } else
                t.add("-");
        }
        if (uh != "")
            t.add(uh);
        if (uhb != "")
            tb.add(uhb);
        for (int j = 0; j < t.size(); j++) {
            cont = 0;
            f = "";
            uh = t.get(j);
            for (int c = 1; c < uh.length(); c++) {
                if (uh.charAt(c - 1) != '(' && uh.charAt(c - 1) != '[' && uh.charAt(c - 1) != ']')
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
        for (int k = 0; k < opers.size(); k++) {
            if (opers.get(k).length() < 1 || opers.get(k).length() == 1 && containsAny(opers.get(k))) {
                opers.remove(opers.get(k));
                k--;
            }
        }
        // System.out.println(opers);
        for (int j = 0; j < tb.size(); j++) {
            cont = 0;
            f = "";
            uhb = tb.get(j);
            for (int c = 1; c < uhb.length(); c++) {
                if (uhb.charAt(c - 1) != '[')
                    f = f.concat(String.valueOf(uhb.charAt(c - 1)));
                else {
                    if (f.length() > 1 || !f.equals("*") && !f.equals("+"))
                        bigPar.add(f);
                    f = "";
                }
                cont = c;
            }
            f = f.concat(String.valueOf(uhb.charAt(cont)));
            bigPar.add(f);
        }
    }

    public static int powCalc(String op) {
        int s;
        char ch;
        for (int i = 0; i < op.length(); i++) {
            ch = op.charAt(i);
            if (!containsAny(String.valueOf(ch)) && ch != '-')
                uniqueChars.add(ch);

        }
        // uniqueChars.toArray();
        s = uniqueChars.size();

        return s;
    }

    public static String negado(String pos) {
        String neg = "";
        for (int i = 0; i < pos.length(); i++) {
            if (pos.charAt(i) == '1')
                neg = neg.concat(String.valueOf('0'));
            else
                neg = neg.concat(String.valueOf('1'));
        }
        return neg;
    }

    public static void valAddr(String op) {
        String val = "";
        int r = powCalc(op), pow = (int) Math.pow(2, r), powh = pow / 2, pos = 0;
        for (int c = 0; c < r; c++) {
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
    }

    public static void opAddr() {
        String val, nom;
        boolean cont = false;
        for (int i = 0; i < opers.size(); i++) {
            if (!opers.get(i).equals("-")) {
                if (cont) {
                    nom = opers.get(i);
                    val = vls.get(i - 1);
                } else {
                    nom = opers.get(i);
                    val = vls.get(i);
                }
                if (nom != "" && val != "") {
                    Operacion o = new Operacion();
                    o.setNom(nom);
                    o.setVals(val);
                    ops.add(o);
                }
            } else
                cont = true;
        }
        char ch = vr.get(vr.size() - 1).getNom();

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

    public static void bigParenth() {
        String va1, va2, cb = "", ca = "", cc = "", test, res, nres;
        char r = ' ';
        boolean neh = false;
        int fin = 0, osint = 0;
        opAddr();
        for (int i = 0; i < bigPar.size(); i++) {
            res = "";
            va1 = "";
            va2 = "";
            test = bigPar.get(i);
            if (test.equals("-")) {
                neh = true;

            } else if (test.length() > 1) {
                for (int j = 0; j < ops.size(); j++) {
                    if (test.length() > 10) {
                        if (test.charAt(0) != '-') {
                            ca = ca.concat(test.substring(1, 4));
                            cc = cc.concat(test.substring(7, 10));
                        } else {
                            ca = ca.concat(test.substring(2, 5));
                            cc = cc.concat(test.substring(8, 11));
                        }
                    } else {
                        ca = "";
                        cc = "";
                        for (int k = 1; k < test.length(); k++) {
                            r = test.charAt(k);
                            if (r != ')')
                                ca = ca.concat(String.valueOf(test.charAt(k)));
                            else {
                                fin = k;
                                break;
                            }
                        }
                        for (int k = fin + 3; k < test.length(); k++) {
                            r = test.charAt(k);
                            if (r != ')')
                                cc = cc.concat(String.valueOf(test.charAt(k)));
                            else
                                break;

                        }
                    }
                    cb = ops.get(j).getNom();
                    if (ca.equals(cb)) {
                        va1 = vr.get(j).getVals();
                        cc = "";
                    }
                    if (cc.equals(cb)) {
                        va2 = vr.get(j).getVals();
                        ca = "";
                    }
                    if (va1 != "" && va2 != "")
                        break;
                }
                if (va1 != "" || va2 != "") {
                    if (test.charAt(0) != '-') {
                        for (int c = 0; c < va1.length(); c++) {
                            r = plusTimes(test.charAt(5), va1.charAt(c), va2.charAt(c));
                            res = res.concat(String.valueOf(r));
                        }
                    } else {
                        for (int c = 0; c < va1.length(); c++) {
                            r = plusTimes(test.charAt(6), va1.charAt(c), va2.charAt(c));
                            res = res.concat(String.valueOf(r));
                            neh = true;
                        }
                    }
                }
                if (neh) {
                    nres = negado(res);
                    os.add(nres);
                    neh = false;
                } else
                    os.add(res);

            } else if (test.length() < 1) {
                bigPar.remove(test);
                i--;
            }
            osint++;

        }
    }

    public static void parenth() {
        String va1, va2, test, res, nres;
        char ca, cb, cc, r;
        boolean neh = false;

        for (int i = 0; i < opers.size(); i++) {
            res = "";
            va1 = "";
            va2 = "";
            test = opers.get(i);
            if (test.equals("-"))
                neh = true;
            else if (test.length() > 1) {
                for (int j = 0; j < vr.size(); j++) {
                    ca = test.charAt(0);
                    cb = vr.get(j).getNom();
                    cc = test.charAt(2);
                    if (ca == cb)
                        va1 = vr.get(j).getVals();
                    if (cc == cb)
                        va2 = vr.get(j).getVals();
                    if (va1 != "" && va2 != "")
                        break;
                }
                if (va1 != "" || va2 != "") {
                    for (int c = 0; c < va1.length(); c++) {
                        r = plusTimes(test.charAt(1), va1.charAt(c), va2.charAt(c));
                        res = res.concat(String.valueOf(r));
                    }
                }
                if (neh) {
                    nres = negado(res);
                    vls.add(nres);
                    neh = false;
                } else
                    vls.add(res);
            } else if (test.length() < 1) {
                opers.remove(test);
                i--;
            } else if (test.equals("ÿ")) {
                vls.add(negado(vr.get(vr.size() - 1).getVals()));
            }

        }
    }

    public static void bigParAddr() {
        String nom = "", val = "", t;
        boolean cont = false;
        for (int a = 0; a < bigPar.size(); a++) {
            t = bigPar.get(a);
            if (!bigPar.get(a).equals("-")) {
                if (cont) {
                    nom = bigPar.get(a);
                    val = os.get(a - 1);
                } else if (!cont) {
                    nom = bigPar.get(a);
                    val = os.get(a);
                }
                if (nom != "" && val != "") {
                    Operacion bigP = new Operacion();
                    bigP.setNom(nom);
                    bigP.setVals(val);
                    bigRes.add(bigP);
                }
            } else
                cont = true;
        }
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
        String a;
        char c;
        // int cont = 0;
        ArrayList<Character> caux = new ArrayList<>(uniqueChars);
        ch = caux;
        // System.out.println(ch);

        for (int i = 0; i < v.size(); i++) {
            a = v.get(i);
            c = ch.get(i);

            Variable vo = new Variable();
            if (c != 'ÿ') {
                vo.setNom(c);
                vo.setVals(a);
            } else {
                vo.setNom('g');
                vo.setVals(a);
            }
            vr.add(vo);
        }
    }

    public static void gather(String op) {
        ArrayList<String> t = new ArrayList<>();
        boolean neg = false;
        String n = "";
        arrayMaker(op);
        valAddr(op);
        assignVar();
        parenth();
        bigParenth();
        bigParAddr();
        System.out.println(opers);

        for (int i = 0; i < vr.size(); i++)
            System.out.println(vr.get(i).getNom() + "\t" + vr.get(i).getVals());

        for (int i = 0; i < ops.size(); i++)
            System.out.println(ops.get(i).getNom() + "\t" + ops.get(i).getVals());

        for (int i = 0; i < bigRes.size(); i++) {
            if (bigPar.get(i).equals("-"))
                neg = true;
            if (!neg)
                System.out.println(bigRes.get(i).getNom() + "\t" + bigRes.get(i).getVals());
            else {
                System.out.println("-" + bigRes.get(i).getNom() + "\t" + bigRes.get(i).getVals());
                neg = false;
            }
        }
    }
}

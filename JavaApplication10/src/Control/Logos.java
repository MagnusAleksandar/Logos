package Control;

import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Logos {
    private static Set<Character> uniqueChars = new TreeSet<>();

    private static ArrayList<String> v = new ArrayList<>();
    private static ArrayList<String> opers = new ArrayList<>();
    private static ArrayList<String> bigPar = new ArrayList<>();
    private static ArrayList<String> vls = new ArrayList<>();
    private static ArrayList<Character> ch = new ArrayList<>(uniqueChars);
    private static ArrayList<Variable> vr = new ArrayList<>();
    private static ArrayList<Operacion> ops = new ArrayList<>();
    private static ArrayList<String> os = new ArrayList<>();
    private static ArrayList<Operacion> bigRes = new ArrayList<>();
    private static ArrayList<Operacion> theEnd = new ArrayList<>();

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
        ArrayList<String> t = new ArrayList<>();
        ArrayList<String> tb = new ArrayList<>();
        String uh = "", uhb = "", f;
        int cont = 1;
        boolean bneg = false;
        char chec;
        for (int i = 0; i < op.length(); i++) {
            chec = op.charAt(i);
            if (chec == '-')
                bneg = true;
            if (chec == '[' && bneg) {
                tb.add(uhb);
                bneg = false;
                uhb = "";
            } else {
                if (chec != ']') {
                    if (chec != '-') {
                        uhb = uhb.concat(String.valueOf(chec));
                        if (chec != ')') {
                            uh = uh.concat(String.valueOf(chec));
                        } else {
                            t.add(uh);
                            uh = "";
                        }
                    } else
                        tb.add(String.valueOf(chec));
                } else {
                    tb.add(uhb);
                    uhb = "";
                }

            }
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
                if (uh.charAt(c - 1) != '(' && uh.charAt(c - 1) != '[')
                    f = f.concat(String.valueOf(uh.charAt(c - 1)));
                else if (!f.isEmpty()) {
                    opers.add(f);
                    f = "";
                }
                cont = c;
            }
            f = f.concat(String.valueOf(uh.charAt(cont)));
            opers.add(f);
        }
        for (int k = 0; k < opers.size(); k++) {
            uh = opers.get(k);
            if (uh.length() < 3 && containsAny(uh)) {
                opers.remove(uh);
                k--;
            }
            if (uh.contains("-"))
                opers.set(k, "-");
        }
        for (int j = 0; j < tb.size(); j++) {
            cont = 0;
            f = "";
            uhb = tb.get(j);
            for (int c = 1; c < uhb.length(); c++) {
                if (uhb.charAt(c - 1) != '[')
                    f = f.concat(String.valueOf(uhb.charAt(c - 1)));
                else if (!f.isEmpty()) {
                    bigPar.add(f);
                    f = "";
                }
                cont = c;
            }
            if (uhb.length() > 5) {
                f = f.concat(String.valueOf(uhb.charAt(cont)));
                bigPar.add(f);
            } else if (!uhb.isEmpty())
                bigPar.add(uhb);
            // f = f.concat(String.valueOf(uhb.charAt(cont)));
        }

        for (int k = 0; k < bigPar.size(); k++) {
            uh = bigPar.get(k);
            if (uh.length() < 5 && containsAny(uh)) {
                bigPar.remove(uh);
                k--;
            }
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
        String val = "", nom = "", t;
        boolean cont = false, dash = false;
        for (int i = 0; i < opers.size(); i++) {
            t = opers.get(i);
            if (!t.equals("-")) {
                if (t.length() >= 1 || t.equals("ÿ")) {
                    if (cont) {
                        if (dash) {
                            nom = "-" + t;
                            dash = false;
                        } else
                            nom = t;
                        val = vls.get(i - 1);
                    } else {
                        nom = t;
                        val = vls.get(i);
                    }
                }
            } else {
                cont = true;
                dash = true;
            }
            if (nom != "" && val != "") {
                Operacion o = new Operacion();
                o.setNom(nom);
                o.setVals(val);
                ops.add(o);
            }
        }
        // for (int l = 0; l < ops.size(); l++)
        // System.out.println(ops.get(l).getNom());
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
        String va1, va2, cb = "", ca = "", cc = "", test, res, nres = "";
        char r = ' ';
        boolean neh = false, notnot = false;
        int fin = 0, s, ind;
        opAddr();
        // System.out.println(bigPar + "gg");
        for (int i = 0; i < bigPar.size(); i++) {
            res = "";
            va1 = "";
            va2 = "";
            test = bigPar.get(i);
            s = test.length();
            if (test.equals("-"))
                neh = true;
            else {
                for (int j = 0; j < ops.size(); j++) {
                    ca = "";
                    cc = "";
                    cb = "";

                    if (test.length() >= 10) {
                        if (test.charAt(0) != '-') {
                            ca = ca.concat(test.substring(1, 4));
                            cc = cc.concat(test.substring(7, 10));
                        } else {
                            ca = ca.concat(test.substring(2, 5));
                            cc = cc.concat(test.substring(8, s - 1));
                        }
                    } else {
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
                    // if (!cc.isEmpty())
                    if (cc.length() < 3 && uniqueChars.contains(cc.charAt(0))
                            || ca.length() < 3 && uniqueChars.contains(ca.charAt(0)))
                        notnot = true;
                    cb = ops.get(j).getNom();
                    if (cb.charAt(0) != '-') {
                        if (ca.equals(cb))
                            va1 = ops.get(j).getVals();

                        if (cc.equals(cb))
                            va2 = ops.get(j).getVals();
                    } else {
                        if (ca.equals(cb.substring(1))) {
                            va1 = ops.get(j).getVals();
                        }
                        if (cc.equals(cb.substring(1)))
                            va2 = ops.get(j).getVals();
                    }
                    if (va1 != "" && va2 != "")
                        break;
                }
            }

            if (notnot) {
                for (int z = 0; z < vr.size(); z++) {
                    cb = String.valueOf(vr.get(z).getNom());
                    if (ca.equals(cb))
                        va1 = vr.get(z).getVals();

                    if (cc.equals(cb))
                        va2 = vr.get(z).getVals();
                }
            }
            if (va1 != "" || va2 != "") {
                if (test.charAt(0) != '-')
                    ind = 5;
                else
                    ind = 6;
                for (int c = 0; c < va1.length(); c++) {
                    r = plusTimes(test.charAt(ind), va1.charAt(c), va2.charAt(c));
                    res = res.concat(String.valueOf(r));
                }
            }
            if (res.length() > 1) {
                if (neh) {
                    res = negado(res);
                    neh = false;
                }
                os.add(res);
            }
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
        ArrayList<String> tempr = new ArrayList<>();
        String nom = "", val = "", t;
        boolean cont = false;
        int post;
        for (int b = 0; b < bigPar.size(); b++) {
            t = bigPar.get(b);
            if (!t.equals("-"))
                tempr.add(t);
        }
        for (int a = 0; a < tempr.size(); a++) {
            nom = tempr.get(a);
            val = os.get(a);
            if (nom != "" && val != "") {
                Operacion bigP = new Operacion();
                bigP.setNom(nom);
                bigP.setVals(val);
                bigRes.add(bigP);
            }
        }
    }

    public static void finalOp(String op) {
        ArrayList<String> tp = new ArrayList<>();
        String rfin = "", n1 = "", n2 = "", n3 = "";
        char r;
        int pos = bigRes.get(0).getNom().length(), s = bigRes.get(0).getNom().length();

        if (op.charAt(0) == '-')
            pos += 3;
        if (op.charAt(0) == '[')
            pos += 2;

        r = op.charAt(pos);
        for (int i = 1; i < bigRes.size(); i++) {
            n1 = bigRes.get(i - 1).getVals();
            if (n2 == "")
                n2 = bigRes.get(i).getVals();
            if (n1 != n2) {
                for (int j = 0; j < n1.length(); j++)
                    rfin = rfin.concat(String.valueOf(plusTimes(r, n1.charAt(j), n2.charAt(j))));
            } else
                n3 = n1;
            if (n3 != "") {
                for (int j = 0; j < n3.length(); j++)
                    rfin = rfin.concat(String.valueOf(plusTimes(r, n3.charAt(j), n2.charAt(j))));
                n3 = "";
            }
            tp.add(rfin);
            rfin = "";
        }
        if (tp.size() == 1) {
            Operacion opf = new Operacion();
            opf.setNom(op);
            opf.setVals(tp.get(0));
            theEnd.add(opf);
        }
    }

    public static void assignVar() {
        String a;
        char c;
        ArrayList<Character> caux = new ArrayList<>(uniqueChars);
        ch = caux;

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
        arrayMaker(op);
        valAddr(op);
        assignVar();
        parenth();
        bigParenth();
        bigParAddr();
        finalOp(op);
    }

    public static void showr() {
        String noms = "", vls = "", tst;
        boolean neg = false;

        for (int i = 0; i < vr.size(); i++)
            noms = noms.concat(String.valueOf(vr.get(i).getNom()) + "\t");
        for (int i = 0; i < ops.size(); i++)
            if (ops.get(i).getNom().length() > 1 || ops.get(i).getNom().equals("ÿ"))
                noms = noms.concat(ops.get(i).getNom() + "\t");
        for (int i = 0; i < bigPar.size(); i++) {
            tst = bigPar.get(i);
            if (tst.equals("-"))
                neg = true;
            else {
                if (!neg)
                    noms = noms.concat(bigPar.get(i) + "\t");
                else {
                    noms = noms.concat("-" + bigPar.get(i) + "\t");
                    neg = false;
                }
            }
        }
        noms = noms.concat(theEnd.get(0).getNom());
        System.out.println(noms);
        for (int j = 0; j < vr.get(0).getVals().length(); j++) {
            for (int k = 0; k < vr.size(); k++)
                vls = vls.concat(String.valueOf(vr.get(k).getVals().charAt(j)) + "\t");
            for (int k = 0; k < ops.size(); k++)
                vls = vls.concat(String.valueOf(ops.get(k).getVals().charAt(j) + "\t"));
            for (int k = 0; k < bigRes.size(); k++)
                vls = vls.concat(String.valueOf(bigRes.get(k).getVals().charAt(j) + "\t\t"));
            vls = vls.concat(String.valueOf(theEnd.get(0).getVals().charAt(j)));
            System.out.println(vls);
            vls = "";
        }

    }
}

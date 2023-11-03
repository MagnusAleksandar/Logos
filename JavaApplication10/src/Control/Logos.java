package Control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
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
    private static ArrayList<String> tbik = new ArrayList<>();
    private static ArrayList<String> tsmol = new ArrayList<>();
    private static ArrayList<String> hugePar = new ArrayList<>();

    public static boolean containsAny(String str) {
        String frb = "+*()[]{}";
        for (char c : frb.toCharArray()) {
            if (str.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }

    public static void separOpers() {
        for (int i = 0; i < bigPar.size(); i++)
            if (bigPar.get(i).length() > 1)
                tbik.add(bigPar.get(i));
        for (int j = 0; j < opers.size(); j++)
            if (opers.get(j).length() > 1)
                tsmol.add(opers.get(j));
    }

    public static void biggstArrMaker(String op) {

        ArrayList<String> tm = new ArrayList<>();
        String uhm = "", f, fm;
        int cont = 1;// -{[(a*b)+(k*l)]*[(c*d)+(i*j)]}+[-[(-(e*f)+(-(g*h)]
        boolean mneg = false;
        char chec;
        for (int i = 0; i < op.length(); i++) {
            chec = op.charAt(i);
            if (chec != '-') {
                if (chec != '}') {
                    uhm = uhm.concat(String.valueOf(chec));
                } else {
                    tm.add(uhm);
                    uhm = "";
                }
            } else
                tm.add(String.valueOf(chec));
        }
        for (int j = 0; j < tm.size(); j++) {
            cont = 0;
            f = "";
            uhm = tm.get(j);
            if (uhm.equals("-"))
                mneg = true;
            if (mneg && uhm.length() > 1) {
                hugePar.add(tm.get(j - 1));
                for (int c = 1; c < uhm.length(); c++) {
                    chec = uhm.charAt(c - 1);
                    if (chec != '{')
                        f = f.concat(String.valueOf(chec));
                    else if (!f.isEmpty()) {
                        hugePar.add(f);
                        f = "";
                    }
                    cont = c;
                }
                f = f.concat(String.valueOf(uhm.charAt(cont)));
                hugePar.add(f);
                mneg = false;
            }
        }
        // System.out.println(hugePar);
    }

    public static void arrayMaker(String op) {
        ArrayList<String> t = new ArrayList<>();
        ArrayList<String> tb = new ArrayList<>();
        String uh = "", uhb = "", f;
        int cont = 1;
        boolean bneg = false;
        char chec;
        if (op.startsWith("-{") || op.startsWith("{")) {
            biggstArrMaker(op);
            StringBuilder sb = new StringBuilder(op);
            sb.deleteCharAt(0);
            if (op.startsWith("-"))
                sb.deleteCharAt(0);
            op = sb.deleteCharAt(sb.indexOf("}")).toString();
            // System.out.println(op);
        }
        for (int i = 0; i < op.length(); i++) {
            chec = op.charAt(i);
            if (chec == '-')
                bneg = true;
            if (chec == '[' && bneg) {
                tb.add(uhb);
                bneg = false;
                uhb = "";
            } else {
                if (chec != '-') {
                    if (chec != ']') {
                        uhb = uhb.concat(String.valueOf(chec));
                        if (chec != ')')
                            uh = uh.concat(String.valueOf(chec));
                        else {
                            t.add(uh);
                            uh = "";
                        }
                    } else {
                        tb.add(uhb);
                        uhb = "";
                    }
                } else
                    uhb = uhb.concat(String.valueOf(chec));

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
                chec = uh.charAt(c - 1);
                if (chec != '(' && chec != '[')
                    f = f.concat(String.valueOf(chec));
                else if (!f.isEmpty()) {
                    opers.add(f);
                    f = "";
                }
                if (uh.charAt(c) == '(' && chec == '(') {
                    f = f.concat(String.valueOf('-'));

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
        }

        for (int k = 0; k < bigPar.size(); k++) {
            uh = bigPar.get(k);
            if (uh.length() < 5 && containsAny(uh) || uh.startsWith("*") || uh.startsWith("+")) {
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
        ArrayList<String> tempr = new ArrayList<>();
        String val = "", nom = "", t;
        // boolean cont = false, dash = false;
        for (int b = 0; b < opers.size(); b++) {
            t = opers.get(b);
            if (!t.equals("-"))
                tempr.add(t);
        }
        for (int i = 0; i < tempr.size(); i++) {
            nom = tempr.get(i);
            val = vls.get(i);
            if (nom != "" && val != "") {
                Operacion o = new Operacion();
                o.setNom(nom);
                o.setVals(val);
                ops.add(o);
            }
        }
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
        String va1, va2, cb = "", ca = "", cc = "", test, res;
        char r = ' ';
        boolean neh = false, notnot = false;
        int fin = 0, s, ind = 0;
        opAddr();
        for (int i = 0; i < bigPar.size(); i++) {
            res = "";
            va1 = "";
            va2 = "";
            test = bigPar.get(i);
            s = test.length();
            if (test.equals("-"))
                neh = true;
            else if (test.length() > 7) {
                for (int j = 0; j < ops.size(); j++) {
                    ca = "";
                    cc = "";
                    cb = "";

                    if (test.length() == 11) {
                        if (test.charAt(0) != '-') {
                            ca = ca.concat(test.substring(1, 4));
                            cc = cc.concat(test.substring(7, 10));
                        } else {
                            ca = ca.concat(test.substring(2, 5));
                            cc = cc.concat(test.substring(8, s - 1));
                        }
                    } else if (test.length() == 15) {
                        if (test.charAt(1) != '-')
                            ca = ca.concat(test.substring(2, 5));
                        else
                            ca = ca.concat(test.substring(3, 6));
                        if (test.charAt(9) != '-')
                            cc = cc.concat(test.substring(10, s - 1));
                        else
                            cc = cc.concat(test.substring(11, s - 1));
                    } else {
                        for (int k = 1; k < test.length(); k++) {
                            r = test.charAt(k);
                            if (r != ')')
                                ca = ca.concat(String.valueOf(r));
                            else {
                                fin = k;
                                break;
                            }
                        }
                        if (test.length() == 9) {
                            fin += 3;
                        } else if (test.length() == 13) {
                            fin += 5;
                        }
                        for (int k = fin; k < test.length(); k++) {
                            r = test.charAt(k);
                            if (r != ')')
                                cc = cc.concat(String.valueOf(test.charAt(k)));
                            else
                                break;
                        }
                    }
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
                    if (!ca.equals(String.valueOf('ÿ')) && !cc.equals(String.valueOf('ÿ'))) {
                        if (ca.equals(cb))
                            va1 = vr.get(z).getVals();
                        else if (cc.equals(cb))
                            va2 = vr.get(z).getVals();
                    } else {
                        if (cb.equals(String.valueOf('g'))) {
                            if (ca.equals(String.valueOf('ÿ'))) {
                                va1 = vr.get(z).getVals();
                                va1 = negado(va1);
                            } else if (cc.equals(String.valueOf('ÿ'))) {
                                va2 = vr.get(z).getVals();
                                va2 = negado(va2);
                            }
                        }
                    }
                }
            }

            if (va1 != "" && va2 != "") {
                if (test.charAt(0) != '-') {
                    if (test.charAt(1) != '-')
                        ind = 5;
                    else
                        ind = 7;
                } else
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
        for (int b = 0; b < bigPar.size(); b++) {
            t = bigPar.get(b);
            if (!t.equals("-") && t.length() > 6)
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
        ArrayList<String> te = new ArrayList<>();
        String rfin = "", n1 = "", n2 = "", n3 = "", t1 = "", t2;
        char r;
        int pos = bigRes.get(0).getNom().length(), s = 0;

        if (!hugePar.isEmpty())
            for (int x = 0; x < hugePar.size(); x++) {
                if (s < hugePar.get(x).length())
                    s = hugePar.get(x).length();
            }

        if (!op.startsWith("(")) {
            pos++;
            if (op.charAt(1) != '(')
                for (int k = 0; k < bigRes.get(0).getNom().length(); k++) {
                    if (op.charAt(k) == '-')
                        pos++;
                    if (op.charAt(k) == '{')
                        pos = s + 2;
                    if (op.charAt(k) == '[')
                        pos++;
                    if (op.charAt(k) == '(')
                        break;
                }
        }

        if (!op.startsWith("-{") && !op.startsWith("{")) {
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
            n1 = "";
            n2 = "";
            n3 = "";
            rfin = "";
            separOpers();
            if (tsmol.size() == (tbik.size() * 2 + 1)) {
                n1 = tp.get(0);
                for (int g = 0; g < tsmol.size(); g++) {
                    t1 = tsmol.get(g);
                    for (int h = 0; h < tbik.size(); h++) {
                        t2 = tbik.get(h);
                        if (t2.contains(t1))
                            n3 = n3.concat(t1);

                    }
                }
                if (!n3.isEmpty())
                    n3 = t1;
                for (int z = 0; z < ops.size(); z++) {
                    System.out.println(ops.get(z).getNom());
                    if (ops.get(z).getNom() == t1) {
                        n2 = ops.get(z).getVals();
                        break;
                    }
                }
                for (int w = 1; w < n1.length(); w++) {
                    rfin = rfin.concat(String.valueOf(plusTimes('+', n1.charAt(w), n2.charAt(w))));
                }

            }
        } else {
            ArrayList<String> tbik = new ArrayList<>();
            tbik = biggestParAddr(op);
            if (tbik.size() < 2) {
                n1 = tbik.get(0);
                for (int j = 0; j < hugePar.size(); j++)
                    for (int i = 0; i < ops.size(); i++)
                        if (!hugePar.get(j).contains(ops.get(i).getNom()))
                            n2 = ops.get(i).getVals();
                for (int l = 0; l < n1.length(); l++) {
                    rfin = rfin.concat(String.valueOf(plusTimes(op.charAt(pos), n1.charAt(l), n2.charAt(l))));
                }
            }
        }
        tp.add(rfin);
        te.add(tp.get(0));
        if (te.size() == 1) {
            Operacion opf = new Operacion();
            opf.setNom(op);
            opf.setVals(te.get(0));
            theEnd.add(opf);
        }
    }

    public static ArrayList<String> biggestParAddr(String op) {
        ArrayList<String> toparr = new ArrayList<>();
        String v = "", t;
        boolean neg = false;
        int pos;
        if (op.startsWith("-"))
            pos = 3;
        else
            pos = 2;
        boolean flag = false;
        for (int i = 0; i < hugePar.size(); i++) {
            t = hugePar.get(i);
            if (!t.equals("-")) {
                Operacion top = new Operacion();
                for (int j = 0; j < bigRes.size(); j++) {
                    if (t.contains(bigRes.get(j).getNom()))
                        flag = true;
                    else if (flag && t.contains(bigRes.get(j).getNom())) {
                        for (int k = 0; k < bigRes.get(j).getVals().length(); k++)
                            v = v.concat(String.valueOf(plusTimes(op.charAt(t.length() + pos),
                                    bigRes.get(j).getVals().charAt(k), bigRes.get(j).getVals().charAt(k - 1))));
                        flag = false;
                    }
                }
                if (neg) {
                    v = negado(v);
                    neg = false;
                }
                toparr.add(v);
                t = "";
                v = "";

            } else
                neg = true;
        }
        return toparr;
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
        int cont = 0;
        File file = new File("output.txt");

        for (int i = 0; i < vr.size(); i++)
            noms = noms.concat(String.valueOf(vr.get(i).getNom()) + "\t");
        for (int i = 0; i < ops.size(); i++)
            if (ops.get(i).getNom().length() > 1 || ops.get(i).getNom().equals("ÿ"))
                noms = noms.concat("M" + (i + 1)) + "\t";
        for (int i = 0; i < bigPar.size(); i++) {
            tst = bigPar.get(i);
            if (!tst.equals("-")) {
                cont++;
                noms = noms.concat("S" + cont + "\t");
            }
        }
        noms = noms.concat("Total\n");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(noms);
            for (int j = 0; j < vr.get(0).getVals().length(); j++) {
                for (int k = 0; k < vr.size(); k++)
                    vls = vls.concat(String.valueOf(vr.get(k).getVals().charAt(j)) + "\t");
                for (int k = 0; k < ops.size(); k++)
                    vls = vls.concat(String.valueOf(ops.get(k).getVals().charAt(j) + "\t"));
                for (int k = 0; k < bigRes.size(); k++)
                    vls = vls.concat(String.valueOf(bigRes.get(k).getVals().charAt(j) + "\t"));
                for (int k = 0; k < theEnd.size(); k++)
                    fileWriter.write(theEnd.get(0).getVals().charAt(k));
                vls = vls.concat(String.valueOf(theEnd.get(0).getVals().charAt(j)));
                fileWriter.write(vls);
                fileWriter.write("\n");
                vls = "";
            }

            fileWriter.close();
            System.out.println("Operación guardada en el archivo 'output.txt'");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}

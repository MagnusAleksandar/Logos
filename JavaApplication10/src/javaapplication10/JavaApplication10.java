package javaapplication10;

import Control.Logos;

public class JavaApplication10 {
    private static Logos l = new Logos();

    public static void main(String[] args) {
        String op = "-(g+j)*(z*g)";
        l.arrayMaker(op);
        l.valAddr(op);
        l.assignVar();
        l.parenth();
        l.finalOp();
    }

}

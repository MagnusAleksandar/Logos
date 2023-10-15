package javaapplication10;

import Control.Logos;

public class JavaApplication10 {
    private static Logos l = new Logos();

    public static void main(String[] args) {
        String op = "[-(a*b)+(c*d)]*[(e*f)+(γ)]";
        l.gather(op);
        // l.finalOp();
    }

}

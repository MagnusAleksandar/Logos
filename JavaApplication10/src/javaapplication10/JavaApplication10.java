package javaapplication10;

import java.util.Scanner;

import Control.Logos;

public class JavaApplication10 {
    private static Logos l = new Logos();

    public static void main(String[] args) {
        // Scanner sn = new Scanner(System.in);
        // System.out.println("Ingrese una operacion: ");
        String op = "[(a*b)+(c*d)]+[(e*f)+(g)]";
        l.gather(op);
        l.showr();
    }

}
// [(a*b)+(c*d)]+[(e*f)+(ÿ)]
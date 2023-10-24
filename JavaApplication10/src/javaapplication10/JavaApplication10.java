package javaapplication10;

import Control.Logos;
import Vista.Most;

public class JavaApplication10 {

    public static void main(String[] args) {
        // Scanner sn = new Scanner(System.in);
        // System.out.println("Ingrese una operacion: ");
        String op = Most.pedirString("Ingrese operacion:");
        System.out.println(op);
        Logos.gather(op);
        Logos.showr();

    }

}
// [(a*b)+(c*d)]+[(e*f)+(ÿ)]
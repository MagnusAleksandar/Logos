package javaapplication10;

import Control.Logos;
import Vista.Most;

public class JavaApplication10 {

    public static void main(String[] args) {
        String op = Most.pedirString("Ingrese operacion:");
        Logos.gather(op);
        Logos.showr();

    }

}
// -[(a*b)+(c*d)]+[(e*f)+(ÿ)]
// -[(a*j)+(-(b*i)]+[-[(c*h)+(d*g)]+(-(e+f)
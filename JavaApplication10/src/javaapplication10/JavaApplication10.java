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
// -{[(a*b)+(k*l)]*[(c*d)+(i*j)]}+[-[(-(e*f)+(-(g*h)]
// -{[(a*b)!(k*l)]*[(c*d)+(i*j)]}%[-[(-(e*f)+(-(g*h)]
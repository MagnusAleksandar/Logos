package Vista;

import javax.swing.JOptionPane;

public class Most {
    public String pedirString(String m) {
        return JOptionPane.showInputDialog(m);
    }

    /*
    public int pedirEntero(String m) {
        return Integer.parseInt(JOptionPane.showInputDialog(m));
    }
    */

    public char pedirChar(String m) {
        return JOptionPane.showInputDialog(m).charAt(0);
    }

    public void mostraDatos(String m) {
        JOptionPane.showMessageDialog(null, m);
    }
}

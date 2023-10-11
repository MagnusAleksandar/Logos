package Control;

public class Variable {
    private String vals;
    private char nom;

    public Variable(String vals, char nom) {
        this.vals = vals;
        this.nom = nom;
    }

    public Variable() {

    }

    public String getVals() {
        return vals;
    }

    public void setVals(String v) {
        this.vals = v;
    }

    public char getNom() {
        return nom;
    }

    public void setNom(char nom) {
        this.nom = nom;
    }

}

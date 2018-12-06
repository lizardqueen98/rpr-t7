package ba.unsa.etf.rpr.tut7;

import java.io.Serializable;
import java.util.ArrayList;

public class UN implements Serializable {
    public ArrayList<Drzava> getDrzave() {
        return drzave;
    }

    public void setDrzave(ArrayList<Drzava> drzave) {
        this.drzave = drzave;
    }

    private ArrayList<Drzava> drzave;
}

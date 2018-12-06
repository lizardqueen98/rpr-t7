package ba.unsa.etf.rpr.tut7;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal {
    public static void main(String[] args){

    }
    public static ArrayList<Grad> ucitajGradove(){
        Scanner ulaz=null;
        try{
            ulaz = new Scanner(new FileReader("mjerenja.txt"));
        }catch(FileNotFoundException e){
            System.out.println("Datoteka se ne moze otvoriti.");
        }
        ArrayList<Grad> gradovi=null;
        Grad g=null;
        while(ulaz.hasNext()){

        }
        return gradovi;
    }
}

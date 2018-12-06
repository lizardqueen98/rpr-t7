package ba.unsa.etf.rpr.tut7;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Tutorijal {
    public static void main(String[] args){
        ArrayList<Grad> gradovi=ucitajGradove();
        Iterator<Grad> it=gradovi.iterator();
        while(it.hasNext()){
            System.out.println(it.next().getNaziv());
        }

    }
    public static ArrayList<Grad> ucitajGradove(){
        Scanner ulaz=null;
        try{
            ulaz = new Scanner(new FileReader("mjerenja.txt"));
        }catch(FileNotFoundException e){
            System.out.println("Datoteka se ne moze otvoriti.");
        }
        ArrayList<Grad> gradovi=new ArrayList<>();
        while(ulaz.hasNext()){
            String[] niz=ulaz.nextLine().split(",");
            Grad g=new Grad();
            g.setNaziv(niz[0]);
            double[] temp=new double[1000];
            for(int i=1;i<niz.length;i++){
                temp[i-1]=(double)Double.valueOf(niz[i]);
            }
            g.setTemperature(temp);
            gradovi.add(g);
        }
        return gradovi;
    }
    public static UN ucitajXml(ArrayList<Grad>){
        Document xmldoc = null;
        try {
            DocumentBuilder docReader
                    = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmldoc = docReader.parse(new File("drzava.xml"));
        } catch (Exception e) {
            System.out.println("drzava.xml nije validan XML dokument");
        }


    }
}

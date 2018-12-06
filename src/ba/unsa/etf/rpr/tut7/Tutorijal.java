package ba.unsa.etf.rpr.tut7;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Tutorijal {
    public static void main(String[] args){
        ArrayList<Grad> lista = ucitajGradove();
        UN un = ucitajXml(lista);
        zapisiXml(un);

        for(int i=0; i<un.getDrzave().size(); i++)
        {
            System.out.println("Ime drzave: "+ un.getDrzave().get(i).getNaziv() );
            System.out.println("Broj stanovnika drzave: "+ un.getDrzave().get(i).getBrojStanovnika() );
            System.out.println("Povrsina: "+ un.getDrzave().get(i).getPovrsina() );
            System.out.println("Jedinica za povrsinu: "+ un.getDrzave().get(i).getJedinicaZaPovrsinu() );
            System.out.println("Glavni grad: "+ un.getDrzave().get(i).getG().getNaziv() );

            System.out.print("Temperature: ");
            for(int j=0; j<1000; j++)
            {
                if(un.getDrzave().get(i).getG().getTemperature()[j]!=0)
                {
                    System.out.print(un.getDrzave().get(i).getG().getTemperature()[j]+" ");
                }

            }
            System.out.println("");
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
    public static UN ucitajXml(ArrayList<Grad> listaGradova){
        Document xmldoc = null;
        try {
            DocumentBuilder docReader
                    = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmldoc = docReader.parse(new File("drzava.xml"));
        } catch (Exception e) {
            System.out.println("drzava.xml nije validan XML dokument");
        }
        UN un = new UN();
        ArrayList<Drzava> drzave=new ArrayList<>();
            NodeList djeca = xmldoc.getElementsByTagName("drzava");
            for(int i = 0; i < djeca.getLength(); i++) {
                Node dijete = djeca.item(i);
                if (dijete instanceof Element) {
                    Element e = (Element) dijete;
                    Drzava drzava =new Drzava();
                    Grad g = new Grad();
                    String naziv;
                    int brojStanovnika;
                    double povrsina;
                    String jedinicaZaPovrsinu;
                    Element povrs=null;
                    brojStanovnika = Integer.parseInt(e.getAttribute("stanovnika"));
                    naziv=e.getElementsByTagName("naziv").item(0).getTextContent();
                    Node jedinica=e.getElementsByTagName("povrsina").item(0);
                    if(jedinica instanceof Element) povrs = (Element) jedinica;
                    jedinicaZaPovrsinu=povrs.getAttribute("jedinica");
                    povrsina=Double.parseDouble(povrs.getTextContent());
                    Node grad=e.getElementsByTagName("glavnigrad").item(0);
                    if(grad instanceof Element){
                        Element element = (Element) grad;
                        String ime;
                        int brStan;
                        ime=element.getElementsByTagName("naziv").item(0).getTextContent();
                        brStan=Integer.parseInt(element.getAttribute("stanovnika"));
                        g.setBrojStanovnika(brStan);
                        g.setNaziv(ime);
                        Iterator<Grad> it=listaGradova.iterator();
                        for(Grad grd:listaGradova){
                            if(grd.getNaziv().equals(ime)) g.setTemperature(grd.getTemperature());
                        }
                    }
                    drzava.setNaziv(naziv);
                    drzava.setBrojStanovnika(brojStanovnika);
                    drzava.setG(g);
                    drzava.setJedinicaZaPovrsinu(jedinicaZaPovrsinu);
                    drzava.setPovrsina(povrsina);
                    drzave.add(drzava);
                }
            }
        un.setDrzave(drzave);
            return un;
    }
    public static void zapisiXml(UN un){
        try {
            XMLEncoder izlaz = new XMLEncoder(new FileOutputStream("un.xml"));
            izlaz.writeObject(un);
            //mora se zatvoriti obavezno
            izlaz.close();
        }
        catch(FileNotFoundException e){

        }
    }
}

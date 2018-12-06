package ba.unsa.etf.rpr.tut7;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

        ArrayList<Grad> ar_gr = ucitajGradove();
        UN un = ucitajXml(ar_gr);

        for(int i=0; i<un.getDrzave().size(); i++)
        {
            System.out.println("naziv drzave: "+ un.getDrzave().get(i).getNaziv() );
            System.out.println("broj stanovnika drzave: "+ un.getDrzave().get(i).getBrojStanovnika() );
            System.out.println("jedinica za povrsinu: "+ un.getDrzave().get(i).getJedinicaZaPovrsinu() );
            System.out.println("povrsina: "+ un.getDrzave().get(i).getPovrsina() );
            System.out.println("glavni grad: "+ un.getDrzave().get(i).getG().getNaziv() );

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
                    String naziv;
                    int brojStanovnika;
                    double povrsina;
                    String jedinicaZaPovrsinu;
                    Grad g = new Grad();
                    brojStanovnika = Integer.parseInt(e.getAttribute("stanovnika"));
                    naziv=e.getElementsByTagName("naziv").item(0).getTextContent();
                    povrsina=Double.parseDouble(e.getElementsByTagName("povrsina").item(0).getTextContent());
                    jedinicaZaPovrsinu=e.getAttribute("jedinica");
                    NodeList gradovi = e.getElementsByTagName("glavnigrad");
                    Node grad=gradovi.item(0);
                    if(grad instanceof Element){
                        Element element = (Element) grad;
                        String ime;
                        int brStan;
                        ime=element.getElementsByTagName("naziv").item(0).getTextContent();
                        brStan=Integer.parseInt(element.getAttribute("stanovnika"));
                        g.setBrojStanovnika(brStan);
                        g.setNaziv(ime);
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
}

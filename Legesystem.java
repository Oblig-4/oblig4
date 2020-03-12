import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;

class Legesystem{
  private Lenkeliste<Pasient> pasienter;
  private SortertLenkeliste<Lege> leger;
  private Lenkeliste<Legemiddel> legemidler;
  //private Lenkeliste<Resept> resepter;

  public Legesystem(){
    pasienter = new Lenkeliste<Pasient>();
    legemidler = new Lenkeliste<Legemiddel>();
    leger = new SortertLenkeliste<Lege>();
  //  resepter = new Lenkeliste<Resept>();

  }


  public void lesFraFil(File filnavn) throws FileNotFoundException, UlovligUtskrift, NumberFormatException, NoSuchElementException{
      Scanner fil;
      try{
        fil = new Scanner(filnavn);
      }
      catch (FileNotFoundException e) {
        throw new FileNotFoundException();
      }

      String linje = "";

  //lager liste med pasienter
      while (fil.hasNextLine()){
        linje = fil.nextLine();

        while (linje.charAt(0) != '#') { //charAt(int index) returnerer char verdi på den gitt indexen.
          linje = fil.nextLine();
          try{
            String[] data = linje.split(",");
            String p = data[0];
            String fodselsnummer = data[1];
            Pasient pasient = new Pasient(p, fodselsnummer);
            pasienter.leggTil(pasient);
          }
          catch(NumberFormatException e){
            linje = fil.nextLine();
          }
        }

  //lager liste med legemidler
        while (linje.charAt(0) != '#') {
          linje = fil.nextLine();
          try{
            String[] data = linje.split(",");
            String navn = data[0];
            String type = data[1];
            double pris = Double.parseDouble(data[2]);
            double virkestoff = Double.parseDouble(data[3]);
            int styrke = 0;
            if (data.length == 5){
              styrke = Integer.parseInt(data[4]);
            }

            if (type == "vanlig") {
              Vanlig vanlig = new Vanlig(navn, pris, virkestoff);
              legemidler.leggTil(vanlig);
            }
            if (type == "narkotisk") { //lower
              Narkotisk narko = new Narkotisk(navn, pris, virkestoff, styrke);
              legemidler.leggTil(narko);
            }
            else if (type == "vanedannende"){
              Vanedannende vane = new Vanedannende(navn, pris, virkestoff, styrke);
              legemidler.leggTil(vane);
            }
          }
          catch(NumberFormatException f){
            linje = fil.nextLine();
          }
        }

  //lager liste med leger -sortert
        while (linje.charAt(0) != '#'){
            linje = fil.nextLine();
          try{
            String[] data = linje.split(",");
            String navn = data[0];
            int id = Integer.parseInt(data[1]);
            if (id == 0){
              Lege l = new Lege(navn);
              leger.leggTil(l);
            }
            Spesialist s = new Spesialist(navn, id);
            leger.leggTil(s);
            }
          catch(NumberFormatException f){
            linje = fil.nextLine();
          }
        }

  //lager liste med resepter
        while (linje.charAt(0) != '#'){
          linje = fil.nextLine();
          try{
            String[] data = linje.split(",");
            int id = Integer.parseInt(data[0]);
            String navn = data[1];
            int pId = Integer.parseInt(data[2]);
            String farge = data[3];
            int reit = 0;
            if (data.length == 5){
              reit = Integer.parseInt(data[4]);
            }
            Lege leg = null;
            Pasient pas = null;
            Legemiddel lm = null;

            if(pId < pasienter.stoerrelse() && id < legemidler.stoerrelse()){
              //finner riktig lege
              for (int i=0; i < leger.stoerrelse(); i++) {
                if(leger.hent(i).hentNavn().equals(navn)) {
                  leg = leger.hent(i);
                 }
               }

              //finner riktig pasient
              for (Pasient p : pasienter) {
                if(p.hentPasientId() == pId){
                  pas = p;
                }
              }

              //finner riktig legemiddel
              for (Legemiddel legem : legemidler) {
                if(legem.hentId() == id){
                  lm = legem;
                }
              }
              if (farge == "p"){
                Resept r = leg.skrivPResept(lm, pas);
                pas.leggTilResept(r);
              }
              else if (farge == "hvit") {
                Resept r = leg.skrivHvitResept(lm, pas, reit);
                pas.leggTilResept(r);
              }
              else if (farge == "millitaer"){
                Resept r = leg.skrivMillitaerResept(lm, pas, reit);
                pas.leggTilResept(r);
              }
              else if (farge == "blaa") {
                Resept r = leg.skrivBlaaResept(lm, pas, reit);
                pas.leggTilResept(r);
              }
            }// slutt på if
            try{
             linje = fil.nextLine();
           }
           catch(NoSuchElementException f){
             break;
          }
        }//slutt på try
        catch(NumberFormatException f){
          linje = fil.nextLine();
        }
      }
    }
  }
}

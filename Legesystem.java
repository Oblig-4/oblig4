import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;
//import java.util.Arrays; test

class Legesystem{
  private Lenkeliste<Pasient> pasienter;
  private SortertLenkeliste<Lege> leger;
  private Lenkeliste<Legemiddel> legemidler;
  private Lenkeliste<Resept> resepter;

  public Legesystem(File filnavn) throws FileNotFoundException, UlovligUtskrift{
    pasienter = new Lenkeliste<Pasient>();
    legemidler = new Lenkeliste<Legemiddel>();
    leger = new SortertLenkeliste<Lege>();
    resepter = new Lenkeliste<Resept>();

    lesFraFil(filnavn);

  }

  public void lesFraFil(File filnavn) throws FileNotFoundException, UlovligUtskrift, NumberFormatException, NoSuchElementException{
      Scanner fil = null;
      try{
        fil = new Scanner(filnavn);
      }
      catch (FileNotFoundException e) {
        System.out.println("Fant ikke filen, gaar tilbake til hovedmeny");
        System.exit(1);//0 : success full termination, 1: exiter med vilje, -1: problem man ikke vet om
      }

      String a = fil.nextLine();

  //lager liste med pasienter
      while (fil.hasNextLine()){
        String linje = fil.nextLine();

        while (linje.charAt(0) != '#') { //charAt(int index) returnerer char verdi på den gitt indexen.
          String[] data = linje.split(",");
          //System.out.println(Arrays.toString(data)); test
            String pasientNavn = data[0];
            String fodselsnummer = data[1];
            Pasient pasient = new Pasient(pasientNavn, fodselsnummer);
            pasienter.leggTil(pasient);
            linje = fil.nextLine();
        }
        break;
      }

  //lager liste med legemidler
      while (fil.hasNextLine()){
      String linje = fil.nextLine();
        while (linje.charAt(0) != '#') {
          String[] data = linje.split(",");
          //System.out.println(Arrays.toString(data)); test
          try{
            String navn = data[0];
            String type = data[1];
            double pris = Double.parseDouble(data[2]);
            double virkestoff = Double.parseDouble(data[3]);
            int styrke = 0;
            linje = fil.nextLine();
            if (data.length == 5){
              styrke = Integer.parseInt(data[4]);
            }

            if (type.equals("vanlig")) {
              Vanlig vanlig = new Vanlig(navn, pris, virkestoff);
              legemidler.leggTil(vanlig);
            }
            else if (type.equals("narkotisk")) {
              Narkotisk narko = new Narkotisk(navn, pris, virkestoff, styrke);
              legemidler.leggTil(narko);
            }
            else if (type.equals("vanedannende")){
              Vanedannende vane = new Vanedannende(navn, pris, virkestoff, styrke);
              legemidler.leggTil(vane);
            }
          }
          catch(NumberFormatException f){
            linje = fil.nextLine();
          }
        }
        break;
      }

  //lager liste med leger -sortert
      while (fil.hasNextLine()){
      String linje = fil.nextLine();
        while (linje.charAt(0) != '#'){
            String[] data = linje.split(",");
            //System.out.println(Arrays.toString(data)); test
          try{
            String navn = data[0];
            int id = Integer.parseInt(data[1]);
            linje = fil.nextLine();
            if (id == 0){
              Lege l = new Lege(navn);
              leger.leggTil(l);
            }
            else{
              Spesialist s = new Spesialist(navn, id);
              leger.leggTil(s);
            }
          }
          catch(NumberFormatException f){
            linje = fil.nextLine();
          }
        }
        break;
      }

  //lager liste med resepter
      while (fil.hasNextLine()){
        String linje = fil.nextLine();
        while (linje.charAt(0) != '#'){
          String[] data = linje.split(",");
          //System.out.println(Arrays.toString(data)); test
          try{
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
                  if (legem instanceof Vanlig) {
                    lm = legem;
                    lm = (Vanlig) lm;
                  }

                  else if (legem instanceof Vanedannende){
                    lm = legem;
                    lm = (Vanedannende) lm;
                  }
                  else if (legem instanceof Narkotisk){
                    lm = legem;
                    lm = (Narkotisk) lm;
                  }
                }
              }


              if (farge.equals("p")){
                try{
                PResept p = leg.skrivPResept(lm, pas);
                pas.leggTilResept(p);
                }
                catch(UlovligUtskrift e){}
              }
              else if (farge.equals("hvit")) {
                try{
                Hvit h = leg.skrivHvitResept(lm, pas, reit);
                pas.leggTilResept(h);
                }
                catch(UlovligUtskrift e){}
                }
              else if (farge.equals("militaer")){
                try{
                  Militaer m = leg.skrivMillitaerResept(lm, pas, reit);
                  pas.leggTilResept(m);
                }
                catch(UlovligUtskrift e){}
              }
              else if (farge.equals("blaa")) {
                try{
                  Blaa b = leg.skrivBlaaResept(lm, pas, reit);
                  pas.leggTilResept(b);
                }
                catch(UlovligUtskrift e){}
              }
            }// slutt på if
          try{
            linje = fil.nextLine();
          }
          catch(NoSuchElementException f){
            break;
          }
        }
        catch(NumberFormatException f){
          linje = fil.nextLine();
        }

      }
      break;
    }
    fil.close();
  }
  public void statistikk(){
    int antVane = 0;
    int antNarko = 0;

    for (Lege l : leger){
      for (Resept r : l.hentUtResepter()){
        resepter.leggTil(r);
      }
    }

    for(int i = 0; i < resepter.stoerrelse(); i++){
      if (resepter.hent(i).hentLegemiddel() instanceof Vanedannende){
        antVane += 1;
      }
      else if (resepter.hent(i).hentLegemiddel() instanceof Narkotisk) {
        antNarko += 1;
      }
    }

    System.out.println(resepter);

    System.out.println("Totalt antall utskrevne resepter paa vanedannende legemidler: " + antVane);
    System.out.println("Totalt antall utskrevne resepter paa narkotiske legemidler: " + antNarko);

    System.out.println("\nStatistikk over utskrevne resepter paa narkotiske resepter \n");

    for(Lege l : leger){
      int antNarkotisk = 0;
      for(Resept r : l.hentUtResepter()){
        if (r.hentLegemiddel() instanceof Narkotisk){
          antNarkotisk +=1;
        }
      }
    System.out.println(l + "\n" + "Antall utskrevne resepter paa narkotiske legemidler:" + antNarkotisk +"\n");
    }
  }
}
